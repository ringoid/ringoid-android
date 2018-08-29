package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.view.presenter.callback.IPresenterSettingsPushListener;
import com.ringoid.view.presenter.util.ISettingsHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettingsPush implements IPresenterSettingsPush {


    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ISettingsHelper settingsHelper;

    private ICacheSettingsPrivacyListener listenerCacheSettings;

    private WeakReference<IPresenterSettingsPushListener> refListener;

    public PresenterSettingsPush() {
        ApplicationRingoid.getComponent().inject(this);
        cacheSettingsPrivacy.addListener(listenerCacheSettings = new ListenerCacheSettings());
    }

    @Override
    public void setListener(IPresenterSettingsPushListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        settingsHelper.requestGet();
        notifyView();
    }

    @Override
    public void updateChecked(int viewId) {
        if (viewId == R.id.ivPushMessages)
            cacheSettingsPrivacy.changeCheckedPushMessages();

        if (viewId == R.id.ivPushMatches)
            cacheSettingsPrivacy.changeCheckedPushMatches();

        if (viewId == R.id.ivPushLikes)
            cacheSettingsPrivacy.changePushLikes();

        settingsHelper.requestSave();
    }

    @Override
    public boolean isChecked(int viewId) {
        if (viewId == R.id.ivPushMessages) return cacheSettingsPrivacy.isCheckedPushMessages();
        if (viewId == R.id.ivPushMatches) return cacheSettingsPrivacy.isCheckedPushMatches();
        if (viewId == R.id.ivPushLikes) return cacheSettingsPrivacy.isPushLikes();
        return false;
    }

    @Override
    public void onSelectLikesType(int resId) {
        cacheSettingsPrivacy.setLikesType(resId == R.id.tvPushLikesAll
                ? 1
                : resId == R.id.tvPushLikesEvery10
                ? 2
                : resId == R.id.tvPushLikesEvery100
                ? 3
                : 0);

        settingsHelper.requestSave();
    }

    private void notifyView() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setData(
                cacheSettingsPrivacy.isCheckedPushMessages(),
                cacheSettingsPrivacy.isCheckedPushMatches(),
                cacheSettingsPrivacy.getPushLikesType());
    }

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {
            notifyView();
        }
    }
}
