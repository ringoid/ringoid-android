/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheLocale;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.request.RequestParamRegisterPhone;
import com.ringoid.controller.data.network.response.ResponseRegisterPhone;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterPhoneListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRegisterPhone implements IRepositoryRegisterPhone {

    @Inject
    ICacheRegister cacheRegister;

    @Inject
    ICacheUser cacheUser;

    @Inject
    IApiRingoid apiRingoid;

    @Inject
    ICacheLocale cacheLocale;

    private Call<ResponseRegisterPhone> request;
    private Callback<ResponseRegisterPhone> requestListener;

    private WeakReference<IRepositoryRegisterPhoneListener> refListener;

    public RepositoryRegisterPhone() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        request = apiRingoid.registerPhone(new RequestParamRegisterPhone(
                cacheUser.getPhoneCode(),
                cacheUser.getPhone().replaceAll("[^0-9]", ""),
                cacheRegister.getDateTerms(),
                cacheRegister.getDateAge(),
                cacheRegister.getDatePrivacy(),
                !cacheRegister.isPhoneValid(),
                cacheLocale.getLang()));
        request.enqueue(requestListener);

    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    @Override
    public void setListener(IRepositoryRegisterPhoneListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifyError() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onError();
    }

    private void notifyErrorPhone() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onErrorPhone();
    }

    private class RequestListener implements Callback<ResponseRegisterPhone> {
        @Override
        public void onResponse(Call<ResponseRegisterPhone> call, Response<ResponseRegisterPhone> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    &&
                    (response.body().isErrorPhone()
                            || response.body().isErrorCode())) {
                notifyErrorPhone();
            } else if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheRegister.setSessionId(response.body().getSessionId());
                cacheUser.setCustomerID(response.body().getCustomerID());
                notifySuccess();
            } else notifyError();
        }

        @Override
        public void onFailure(Call<ResponseRegisterPhone> call, Throwable t) {
            if (call.isCanceled()) return;
            notifyError();
        }
    }
}
