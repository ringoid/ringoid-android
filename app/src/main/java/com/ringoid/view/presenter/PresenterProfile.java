/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.app.AppCompatActivity;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.controller.data.repository.IRepositoryProfilePhotos;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.callback.IPresenterProfileListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterProfile implements IPresenterProfile {

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    INavigator navigator;

    @Inject
    IRepositoryProfilePhotos repositoryProfilePhotos;

    private ListenerCacheProfile listenerCache;
    private WeakReference<IPresenterProfileListener> refListener;

    public PresenterProfile() {
        ApplicationRingoid.getComponent().inject(this);
        cacheProfile.addListener(listenerCache = new ListenerCacheProfile());
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public void onClickPhotoAdd(AppCompatActivity activity) {
        navigator.navigatePhotoAdd(activity);
    }

    @Override
    public void onClickSettings() {
        navigator.navigateSettings();
    }

    @Override
    public void onClickToolbar() {
        navigator.navigateWelcome(false);
    }

    @Override
    public void onCreateView() {
        repositoryProfilePhotos.request();
    }

    @Override
    public void setListener(IPresenterProfileListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void updateView() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().updateView();
    }

    private class ListenerCacheProfile implements ICacheProfileListener {
        @Override
        public void onUpdate() {
            updateView();
        }
    }
}
