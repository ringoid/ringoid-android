package com.ringoid.controller.data.repository;

import org.byters.mockserver.MockServer;
import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class RepositoryRegister implements IRepositoryRegister {
    @Inject
    ICacheRegister cacheRegister;
    private WeakReference<IRepositoryRegisterListener> refListener;

    public RepositoryRegister() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request(String phone) {
        //todo implement
        MockServer.requestRegisterCode(phone);
        notifySuccess();
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    @Override
    public void setListener(IRepositoryRegisterListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
