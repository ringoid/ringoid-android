/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

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
