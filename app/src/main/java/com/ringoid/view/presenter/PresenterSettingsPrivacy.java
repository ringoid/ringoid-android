/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;

import java.lang.ref.WeakReference;

public class PresenterSettingsPrivacy implements IPresenterSettingsPrivacy {

    private ICacheSettingsPrivacyListener listenerCacheSettings;

    private WeakReference<IPresenterSettingsPrivacyListener> refListener;

    public PresenterSettingsPrivacy() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setListener(IPresenterSettingsPrivacyListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        notifyListenersPrivacyMessagesFirst(0);
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

}
