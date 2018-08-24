/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRegisterConfirm implements IRepositoryRegisterCodeConfirm {

    @Inject
    ICacheToken cacheToken;

    @Inject
    IApiRingoid apiRingoid;

    private Call<ResponseRegisterCodeConfirm> request;
    private Callback<ResponseRegisterCodeConfirm> requestListener;

    private WeakReference<IRepositoryRegisterConfirmListener> refListener;

    public RepositoryRegisterConfirm() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void setListener(IRepositoryRegisterConfirmListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Inject
    ICacheRegister cacheRegister;

    @Override
    public void request(int code) {
        if (request != null) request.cancel();

        request = apiRingoid.registerCodeConfirm(
                cacheRegister.getSessionId(),
                code);
        request.enqueue(requestListener);
    }

    private void notifySuccess(boolean isRegistered) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess(isRegistered);
    }

    private class RequestListener implements Callback<ResponseRegisterCodeConfirm> {

        @Override
        public void onResponse(Call<ResponseRegisterCodeConfirm> call, Response<ResponseRegisterCodeConfirm> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheToken.setToken(response.body().getToken());
                notifySuccess(response.body().isRegistered());
            }
        }

        @Override
        public void onFailure(Call<ResponseRegisterCodeConfirm> call, Throwable t) {

        }
    }
}
