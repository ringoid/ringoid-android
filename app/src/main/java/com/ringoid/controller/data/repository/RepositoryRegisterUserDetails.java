package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.util.Log;
import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.request.RequestParamRegisterUserDetails;
import com.ringoid.controller.data.network.response.ResponseRegisterUser;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterUserDetailsListener;
import com.ringoid.model.SEX;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import java.lang.ref.WeakReference;
import java.util.Date;

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

    private WeakReference<IRepositoryRegisterUserDetailsListener> refListener;
    private Call<ResponseRegisterUser> request;
    private Callback<ResponseRegisterUser> requestListener;

    public RepositoryRegisterUserDetails() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void setListener(IRepositoryRegisterUserDetailsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void request() {

        if (request != null) request.cancel();

        request = apiRingoid.getAPI().registerUserDetails(new RequestParamRegisterUserDetails(
                cacheRegister.getYearBirth(),
                cacheRegister.getSex() == SEX.MALE.getValue() ? "male" : "female",
                new Date().getTime(),
                new Date().getTime(),
                new Date().getTime(),
                "en",
                "Model test",
                "Android test"));
        request.enqueue(requestListener);
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private class RequestListener implements Callback<ResponseRegisterUser> {
        @Override
        public void onResponse(Call<ResponseRegisterUser> call, Response<ResponseRegisterUser> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheToken.setToken(response.body().getAccessToken());
                cacheUser.setRegistered(true);
                cacheUser.setUserNew();
                notifySuccess();
            }
        }

        @Override
        public void onFailure(Call<ResponseRegisterUser> call, Throwable t) {
        }
    }
}
