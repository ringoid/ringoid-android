/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.controller.data.repository.IRepositorySettingsGet;
import com.ringoid.controller.data.repository.IRepositorySettingsSave;
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
    public void onClickDistance(int type) {
        cacheSettingsPrivacy.setPrivacyDistance(type);
        settingsHelper.requestSave();
        notifyListenersDistance();
    }

    private void notifyListenersDistance() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setDistance(cacheSettingsPrivacy.getDistanceType());
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

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener{
        @Override
        public void onUpdate(){
            notifyListenersDistance();
        }
    }
}
