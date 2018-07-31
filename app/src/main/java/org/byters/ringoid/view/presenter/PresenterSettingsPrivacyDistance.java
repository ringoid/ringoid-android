package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import org.byters.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettingsPrivacyDistance implements IPresenterSettingsPrivacyDistance {

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;
    private WeakReference<IPresenterSettingsPrivacyDistanceListener> refListener;

    public PresenterSettingsPrivacyDistance() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClickDistance(int type) {
        cacheSettingsPrivacy.setPrivacyDistance(type);
        notifyListenersDistance();
    }

    private void notifyListenersDistance() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setDistance(cacheSettingsPrivacy.getDistanceType());
    }

    @Override
    public void onCreateView() {
        notifyListenersDistance();
    }

    @Override
    public void setListener(IPresenterSettingsPrivacyDistanceListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
