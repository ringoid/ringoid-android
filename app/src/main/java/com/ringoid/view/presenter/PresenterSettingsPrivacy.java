/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheBlacklist;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;
import com.ringoid.view.presenter.util.ISettingsHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettingsPrivacy implements IPresenterSettingsPrivacy {

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ICacheBlacklist cacheBlacklist;

    @Inject
    ISettingsHelper settingsHelper;

    private ICacheSettingsPrivacyListener listenerCacheSettings;

    private WeakReference<IPresenterSettingsPrivacyListener> refListener;

    public PresenterSettingsPrivacy() {
        ApplicationRingoid.getComponent().inject(this);
        cacheSettingsPrivacy.addListener(listenerCacheSettings = new ListenerCacheSettings());
    }

    @Override
    public void onClickPrivacyPhotosAll() {
        cacheSettingsPrivacy.setPrivacyPhotos(0);
        settingsHelper.requestSave();
    }

    private void notifyListenersPrivacyPhotos() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPrivacyPhotos(cacheSettingsPrivacy.getPrivacyPhotos());
    }

    @Override
    public void onClickPrivacyPhotosLikes() {
        cacheSettingsPrivacy.setPrivacyPhotos(1);
        settingsHelper.requestSave();
    }

    @Override
    public void onClickPrivacyPhotosNoone() {
        cacheSettingsPrivacy.setPrivacyPhotos(2);
        settingsHelper.requestSave();
    }

    @Override
    public void setListener(IPresenterSettingsPrivacyListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        settingsHelper.requestGet();
        notifyListenersPrivacyMessagesFirst(0);
        notifyListenersPrivacyBlacklistNum();
    }

    private void notifyListenersPrivacyBlacklistNum() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPrivacyPhoneNum(cacheBlacklist.getItemsNum());

    }

    @Override
    public void onClickMessageFirstMatched() {
        notifyListenersPrivacyMessagesFirst(0);
    }

    @Override
    public void onClickMessageFirstOnlyMe() {
        notifyListenersPrivacyMessagesFirst(1);
    }

    private void notifyListenersPrivacyMessagesFirst(int type) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPrivacyMessagesFirst(type);
    }

    private void notifyListenersPrivacyDistance() {
        if (refListener == null || refListener.get() == null) return;

        int distance = cacheSettingsPrivacy.getDistance();
        refListener.get().setPrivacyDistance(distance == 0 ? R.string.distance_0
                : distance == 10 ? R.string.distance_1
                : distance == 25 ? R.string.settings_privacy_distance_25
                : distance == 50 ? R.string.distance_2
                : distance == 100 ? R.string.distance_3
                : R.string.distance_4);
    }

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {
            notifyListenersPrivacyPhotos();
            notifyListenersPrivacyDistance();
        }
    }
}
