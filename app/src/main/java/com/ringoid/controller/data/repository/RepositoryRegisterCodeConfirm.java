/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.request.RequestParamRegisterCodeConfirm;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterCodeConfirmListener;
import com.ringoid.view.ui.util.ApiRingoidProvider;

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
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheRegister cacheRegister;

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

    @Override
    public void request(String code) {
        if (request != null && !request.isExecuted()) return;

        request = apiRingoid.getAPI().registerCodeConfirm(new RequestParamRegisterCodeConfirm(
                cacheRegister.getSessionId(),
                code));
        request.enqueue(requestListener);
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private void notifyNoPendingClient() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onErrorNoPendingClient();
    }

    private void notifyInvalidVerificationCode() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onErrorInvalidCode();
    }

    private void notifyError() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onErrorUnknown();
    }

    private class RequestListener implements Callback<ResponseRegisterCodeConfirm> {

        @Override
        public void onResponse(Call<ResponseRegisterCodeConfirm> call, Response<ResponseRegisterCodeConfirm> response) {

            if (response.isSuccessful()
                    && response.body() != null) {

                if (response.body().isInavlidVerificationCode())
                    notifyInvalidVerificationCode();

                if (response.body().isNoPendingClient())
                    notifyNoPendingClient();

                if (response.body().isSuccess()) {

                    cacheToken.setToken(response.body().getToken());
                    cacheUser.setRegistered(response.body().isRegistered());
                    if (response.body().isRegistered())
                        cacheUser.setUserOld();
                    notifySuccess();
                }
                return;
            }

            notifyError();

        }

        @Override
        public void onFailure(Call<ResponseRegisterCodeConfirm> call, Throwable t) {
            if (call.isCanceled()) return;
            notifyError();
        }
    }
}
