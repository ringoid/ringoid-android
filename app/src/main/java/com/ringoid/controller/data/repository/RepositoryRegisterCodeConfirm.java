/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.request.RequestParamRegisterCodeConfirm;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterCodeConfirmListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRegisterCodeConfirm implements IRepositoryRegisterCodeConfirm {

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheUser cacheUser;

    @Inject
    IApiRingoid apiRingoid;

    private Call<ResponseRegisterCodeConfirm> request;
    private Callback<ResponseRegisterCodeConfirm> requestListener;

    private WeakReference<IRepositoryRegisterCodeConfirmListener> refListener;

    public RepositoryRegisterCodeConfirm() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void setListener(IRepositoryRegisterCodeConfirmListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Inject
    ICacheRegister cacheRegister;

    @Override
    public void request(String code) {
        if (request != null) request.cancel();

        request = apiRingoid.registerCodeConfirm(new RequestParamRegisterCodeConfirm(
                cacheRegister.getSessionId(),
                code));
        request.enqueue(requestListener);
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private class RequestListener implements Callback<ResponseRegisterCodeConfirm> {

        @Override
        public void onResponse(Call<ResponseRegisterCodeConfirm> call, Response<ResponseRegisterCodeConfirm> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheToken.setToken(response.body().getToken());
                cacheUser.setRegistered(response.body().isRegistered());
                notifySuccess();
            }
        }

        @Override
        public void onFailure(Call<ResponseRegisterCodeConfirm> call, Throwable t) {

        }
    }
}
