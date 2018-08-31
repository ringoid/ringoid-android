/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;
import com.ringoid.view.presenter.util.ISettingsHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettingsPrivacyDistance implements IPresenterSettingsPrivacyDistance {

    private final ICacheSettingsPrivacyListener listenerCacheSettings;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ISettingsHelper settingsHelper;

    private WeakReference<IPresenterSettingsPrivacyDistanceListener> refListener;

    public PresenterSettingsPrivacyDistance() {
        ApplicationRingoid.getComponent().inject(this);
        cacheSettingsPrivacy.addListener(listenerCacheSettings = new ListenerCacheSettings());
    }

    @Override
    public void onClickDistance(int resId) {

        int distance =
                resId == R.id.tvDistance0
                        ? 0
                        : resId == R.id.tvDistance10
                        ? 10
                        : resId == R.id.tvDistance25
                        ? 25
                        : resId == R.id.tvDistance50
                        ? 50
                        : resId == R.id.tvDistance100
                        ? 100
                        : resId == R.id.tvDistance250
                        ? 250
                        : 0;

        cacheSettingsPrivacy.setPrivacyDistance(distance);
        settingsHelper.requestSave();
        notifyListenersDistance();
    }

    private void notifyListenersDistance() {
        if (refListener == null || refListener.get() == null) return;

        int distance = cacheSettingsPrivacy.getDistance();
        refListener.get().setDistance(
                distance == 0
                        ? R.id.tvDistance0
                        : distance == 10
                        ? R.id.tvDistance10
                        : distance == 25
                        ? R.id.tvDistance25
                        : distance == 50
                        ? R.id.tvDistance50
                        : distance == 100
                        ? R.id.tvDistance100
                        : distance == 250
                        ? R.id.tvDistance250
                        : 0);
    }

    @Override
    public void onCreateView() {
        settingsHelper.requestGet();
        notifyListenersDistance();
    }

    @Override
    public void setListener(IPresenterSettingsPrivacyDistanceListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {
            notifyListenersDistance();
        }
    }
}
