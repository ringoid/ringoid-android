package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheBlacklist;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.callback.IPresenterSettingsListener;
import com.ringoid.view.presenter.util.ISettingsHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettings implements IPresenterSettings {

    private final ListenerCacheSettingsPrivacy listenerCacheSettingsPrivacy;

    @Inject
    ICacheBlacklist cacheBlacklist;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ISettingsHelper settingsHelper;

    @Inject
    INavigator navigator;

    private WeakReference<IPresenterSettingsListener> refListener;

    public PresenterSettings() {
        ApplicationRingoid.getComponent().inject(this);
        cacheSettingsPrivacy.addListener(listenerCacheSettingsPrivacy = new ListenerCacheSettingsPrivacy());
    }

    @Override
    public void onCreateView() {
        settingsHelper.requestGet();
    }

    @Override
    public void setListener(IPresenterSettingsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifyListenersPrivacyBlacklistNum() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPrivacyPhoneNum(cacheBlacklist.getItemsNum());
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

    @Override
    public void onClickPrivacyBlacklist() {
        navigator.navigateBlacklistPhones();
    }

    @Override
    public void onClickPrivacyDistance() {
        navigator.navigateSettingsPrivacyDistance();
    }

    @Override
    public void onClickPrivacyLikesMatches() {
        cacheSettingsPrivacy.setPrivacyLikes(1);
    }

    @Override
    public void onClickPrivacyLikesLiked() {
        cacheSettingsPrivacy.setPrivacyLikes(0);
    }


    private class ListenerCacheSettingsPrivacy implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {
            notifyListenersPrivacyBlacklistNum();
            notifyListenersPrivacyDistance();
        }
    }

}
