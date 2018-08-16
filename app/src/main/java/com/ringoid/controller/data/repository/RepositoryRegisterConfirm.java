package com.ringoid.controller.data.repository;

import com.google.gson.Gson;

import org.byters.mockserver.MockServer;
import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class RepositoryRegisterConfirm implements IRepositoryRegisterConfirm {

    @Inject
    ICacheToken cacheToken;
    private WeakReference<IRepositoryRegisterConfirmListener> refListener;

    public RepositoryRegisterConfirm() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setListener(IRepositoryRegisterConfirmListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void request(String textCheck) {
        //todo implement
        ResponseRegisterCodeConfirm response = new Gson().fromJson(MockServer.requestRegisterCodeConfirm(textCheck), ResponseRegisterCodeConfirm.class);

        cacheToken.setToken(response.getToken());
        notifySuccess(response.isRegistered());
    }

    private void notifySuccess(boolean isRegistered) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess(isRegistered);
    }
}
