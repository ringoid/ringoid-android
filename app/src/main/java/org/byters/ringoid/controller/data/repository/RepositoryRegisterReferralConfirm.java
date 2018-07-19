package org.byters.ringoid.controller.data.repository;

import org.byters.mockserver.MockServer;
import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.memorycache.ICacheToken;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterReferralConfirmListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class RepositoryRegisterReferralConfirm implements IRepositoryRegisterReferralConfirm {
    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheRegister cacheRegister;

    private WeakReference<IRepositoryRegisterReferralConfirmListener> refListener;

    public RepositoryRegisterReferralConfirm() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request(String linkReferral) {
        String token = MockServer.requestRegisterReferral(linkReferral, cacheRegister.getSex(), cacheRegister.getDateBirthMillis());
        cacheToken.setToken(token);
        notifyListenersSuccess();
    }

    private void notifyListenersSuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    @Override
    public void setListener(IRepositoryRegisterReferralConfirmListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
