package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Build;

import com.crashlytics.android.Crashlytics;
import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheLocale;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.request.RequestParamCreateProfile;
import com.ringoid.controller.data.network.response.ResponseCreateProfile;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterUserDetailsListener;
import com.ringoid.model.SEX;
import com.ringoid.view.presenter.util.IHelperConnection;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRegisterUserDetails implements IRepositoryRegisterUserDetails {

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheRegister cacheRegister;

    @Inject
    ICacheUser cacheUser;

    @Inject
    ICacheLocale cacheLocale;

    @Inject
    IHelperConnection helperConnection;

    private WeakReference<IRepositoryRegisterUserDetailsListener> refListener;
    private Call<ResponseCreateProfile> request;
    private Callback<ResponseCreateProfile> requestListener;

    public RepositoryRegisterUserDetails() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void setListener(IRepositoryRegisterUserDetailsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifyErrorNoConnection() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onErrorNoConnection();
    }

    private void notifyUnknownError() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onErrorUnknown();
    }

    @Override
    public void request() {

        if (request != null) request.cancel();

        if (!helperConnection.isConnectionExist()) {
            notifyErrorNoConnection();
            return;
        }

        cacheUser.setRegistered(false);
        request = apiRingoid.getAPI().createProfile(new RequestParamCreateProfile(
                cacheRegister.getYearBirth(),
                cacheRegister.getSex() == SEX.MALE.getValue() ? "male" : "female",
                cacheRegister.getDateLegal(),
                cacheRegister.getDateLegal(),
                cacheRegister.getDateLegal(),
                cacheLocale.getLang(),
                String.format("%s, %d", Build.VERSION.RELEASE, Build.VERSION.SDK_INT),
                String.format("%s, %s, %s", Build.MODEL, Build.MANUFACTURER, Build.PRODUCT)));
        request.enqueue(requestListener);
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private class RequestListener implements Callback<ResponseCreateProfile> {
        @Override
        public void onResponse(Call<ResponseCreateProfile> call, Response<ResponseCreateProfile> response) {

            if (response.isSuccessful()
                    && response.body() != null) {

                if (response.body().isWrongYearOfBirthClientError()
                        || response.body().isWrongSexClientError()) {
                    Crashlytics.logException(new RuntimeException(response.body().toString()));
                    notifyUnknownError();
                    return;
                }

                if (response.body().isSuccess()) {
                    cacheToken.setToken(response.body().getAccessToken());
                    cacheUser.setCustomerID(response.body().getCustomerId());
                    cacheUser.setRegistered(true);
                    cacheUser.setUserNew();

                    notifySuccess();
                }
            } else {
                Crashlytics.logException(new RuntimeException("[" + response.code() + "]"));
                notifyUnknownError();
            }
        }

        @Override
        public void onFailure(Call<ResponseCreateProfile> call, Throwable t) {
            if (call.isCanceled()) {
                return;
            }

            if (!helperConnection.isConnectionExist()) {
                notifyErrorNoConnection();
                return;
            }

            Crashlytics.logException(new RuntimeException(t));
            notifyUnknownError();
        }
    }
}
