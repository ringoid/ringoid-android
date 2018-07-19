package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheRegisterReferralListener;

import java.lang.ref.WeakReference;

public class CacheRegisterReferral implements ICacheRegisterReferral {
    private WeakReference<ICacheRegisterReferralListener> refListener;
    private String title;
    private String description;

    @Override
    public void setListener(ICacheRegisterReferralListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void setData(String title, String description) {
        this.title = title;
        this.description = description;
        notifyListeners();
    }

    private void notifyListeners() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdate(title, description);
    }
}
