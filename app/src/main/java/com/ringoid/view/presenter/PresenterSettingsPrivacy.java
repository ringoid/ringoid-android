/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettingsPrivacy implements IPresenterSettingsPrivacy {

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;
    private WeakReference<IPresenterSettingsPrivacyListener> refListener;

    public PresenterSettingsPrivacy() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClickPrivacyPhotosAll() {
        cacheSettingsPrivacy.setPrivacyPhotos(0);
        notifyListenersPrivacyPhotos();
    }

    private void notifyListenersPrivacyPhotos() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPrivacyPhotos(cacheSettingsPrivacy.getPrivacyPhotos());
    }

    @Override
    public void onClickPrivacyPhotosLikes() {
        cacheSettingsPrivacy.setPrivacyPhotos(1);
        notifyListenersPrivacyPhotos();
    }

    @Override
    public void onClickPrivacyPhotosNoone() {
        cacheSettingsPrivacy.setPrivacyPhotos(2);
        notifyListenersPrivacyPhotos();
    }

    @Override
    public void setListener(IPresenterSettingsPrivacyListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        notifyListenersPrivacyPhotos();
        notifyListenersPrivacyDistance();
    }

    private void notifyListenersPrivacyDistance() {
        if (refListener == null || refListener.get() == null) return;

        int type = cacheSettingsPrivacy.getDistanceType();
        refListener.get().setPrivacyDistance(type == 0 ? R.string.distance_0
                : type == 1 ? R.string.distance_1
                : type == 2 ? R.string.distance_2
                : type == 3 ? R.string.distance_3
                : R.string.distance_4);
    }
}
