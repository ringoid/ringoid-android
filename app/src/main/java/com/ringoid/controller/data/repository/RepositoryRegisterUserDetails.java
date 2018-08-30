package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.request.RequestParamRegisterUserDetails;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterUserDetailsListener;
import com.ringoid.model.SEX;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRegisterUserDetails implements IRepositoryRegisterUserDetails {

    @Inject
    IApiRingoid apiRingoid;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheRegister cacheRegister;

    @Inject
    ICacheUser cacheUser;

    private WeakReference<IRepositoryRegisterUserDetailsListener> refListener;
    private Call<ResponseBase> request;
    private Callback<ResponseBase> requestListener;

    public RepositoryRegisterUserDetails() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setListener(IRepositoryRegisterUserDetailsListener listener) {
        this.refListener = new WeakReference<>(listener);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {

        if (request != null) request.cancel();

        request = apiRingoid.registerUserDetails(new RequestParamRegisterUserDetails(
                cacheToken.getToken(),
                cacheRegister.getYearBirth(),
                cacheRegister.getSex() == SEX.MALE.getValue() ? "male" : "female"));
        request.enqueue(requestListener);
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private class RequestListener implements Callback<ResponseBase> {
        @Override
        public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheUser.setRegistered(true);
                notifySuccess();
            }
        }

        @Override
        public void onFailure(Call<ResponseBase> call, Throwable t) {

        }
    }
}
