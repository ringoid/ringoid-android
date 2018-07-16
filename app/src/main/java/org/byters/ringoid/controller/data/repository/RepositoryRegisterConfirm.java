package org.byters.ringoid.controller.data.repository;

import org.byters.mockserver.MockServer;
import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheToken;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;

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
        String token = MockServer.requestRegisterConfirm(textCheck);

        cacheToken.setToken(token);
        notifySuccess();
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }
}
