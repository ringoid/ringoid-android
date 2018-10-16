/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.controller.data.repository.IRepositoryProfileImageRemove;
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;
import com.ringoid.view.presenter.callback.IPresenterAdapterProfileListener;
import com.ringoid.view.presenter.util.IHelperConnection;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterProfile implements IPresenterAdapterProfile {

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    INavigator navigator;

    @Inject
    INavigatorPages navigatorPages;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    IRepositoryProfileImageRemove repositoryProfileImageRemove;

    @Inject
    ICacheUser cacheUser;

    @Inject
    IHelperConnection helperConnection;

    private ListenerCacheProfile listenerCacheProfile;
    private WeakReference<IPresenterAdapterProfileListener> refListener;

    public PresenterAdapterProfile() {
        ApplicationRingoid.getComponent().inject(this);
        cacheProfile.addListener(listenerCacheProfile = new ListenerCacheProfile());
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public String getUrl(int pos) {
        return cacheProfile.getImage(pos);
    }

    @Override
    public int getLikesNum(int position) {
        return cacheProfile.getLikesNum(position);
    }

    @Override
    public void onCLickLikes() {
        navigatorPages.navigateLikes();
    }

    @Override
    public void setListener(IPresenterAdapterProfileListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public String getImageId(int position) {
        return cacheProfile.getImageId(position);
    }

    @Override
    public void onImageRemove(String imageId) {
        if (!helperConnection.isConnectionExist()) {
            navigator.navigateErrorConnection();
            return;
        }

        cacheProfile.removeItem(imageId);
        if (!cacheProfile.isDataExist())
            cacheUser.setUserNew();
        repositoryProfileImageRemove.request(imageId);
    }

    @Override
    public boolean isPhotoLocal(int position) {
        return cacheProfile.isPhotoLocal(position);
    }

    @Override
    public String getUrlThumbnail(int position) {
        return cacheProfile.getUrlThumbnail(position);
    }

    private void updateView() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdate();
    }

    private void updateViewRemove(int position) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdateRemove(position);
    }

    private void updateViewAdd(int position) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdateAdd(position);

    }

    private class ListenerCacheProfile implements ICacheProfileListener {
        @Override
        public void onUpdate() {
            updateView();
        }

        @Override
        public void onPhotoAdd(int position) {
            updateViewAdd(position);
        }

        @Override
        public void onPhotoRemove(int position) {
            updateViewRemove(position);
        }

    }
}
