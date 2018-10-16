/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.controller.data.repository.IRepositoryProfilePhotos;
import com.ringoid.controller.data.repository.callback.IRepositoryProfilePhotosListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.presenter.callback.IPresenterProfileListener;
import com.ringoid.view.presenter.util.IHelperConnection;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterProfile implements IPresenterProfile {

    private static final int DEFAULT_VALUE = -1;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    INavigator navigator;

    @Inject
    IRepositoryProfilePhotos repositoryProfilePhotos;

    @Inject
    IHelperConnection helperConnection;

    private ListenerRepositoryProfile listenerRepositoryProfile;
    private ListenerCacheProfile listenerCache;
    private WeakReference<IPresenterProfileListener> refListener;

    public PresenterProfile() {
        ApplicationRingoid.getComponent().inject(this);
        cacheProfile.addListener(listenerCache = new ListenerCacheProfile());
        repositoryProfilePhotos.setListener(listenerRepositoryProfile = new ListenerRepositoryProfile());
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public void onClickPhotoAdd() {
        if (!helperConnection.isConnectionExist()) {
            navigator.navigateErrorConnection();
            return;
        }
        navigator.navigatePhotoAdd();
    }

    @Override
    public void onClickToolbar() {
    }

    @Override
    public void onCreateView() {
        if (!cacheProfile.isDataExist())
            repositoryProfilePhotos.request();
        else updateView();
    }

    @Override
    public void setListener(IPresenterProfileListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onSwipeRefresh() {
        if (!helperConnection.isConnectionExist()) {
            navigator.navigateErrorConnection();
            return;
        }
        repositoryProfilePhotos.request();
    }

    @Override
    public void onShownItem(int pos) {
        cacheInterfaceState.setProfileOriginPhotoId(cacheProfile.getOriginPhotoId(pos));
    }

    private void updateView() {
        updateView(cacheProfile.getPosition(cacheInterfaceState.getOriginPhotoId(), DEFAULT_VALUE));
    }

    private void updateView(int position) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().updateView(position);
    }

    private void notifyRefreshComplete() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().refreshComplete();
    }

    private class ListenerCacheProfile implements ICacheProfileListener {
        @Override
        public void onUpdate() {
            updateView();
        }

        @Override
        public void onPhotoAdd(int position) {
            updateView(position);
        }

        @Override
        public void onPhotoRemove(int index) {
            onUpdate();
        }
    }

    private class ListenerRepositoryProfile implements IRepositoryProfilePhotosListener {
        @Override
        public void onSuccess() {
            notifyRefreshComplete();
        }

        @Override
        public void onError() {
            notifyRefreshComplete();
        }
    }
}
