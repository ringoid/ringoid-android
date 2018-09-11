/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheScrollListener;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.controller.data.repository.IRepositoryProfilePhotos;
import com.ringoid.controller.data.repository.callback.IRepositoryProfilePhotosListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;
import com.ringoid.view.INavigatorPagesListener;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;
import com.ringoid.view.presenter.util.ISettingsHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterPagesContainer implements IPresenterPagesContainer {

    @Inject
    INavigatorPages navigatorPages;

    @Inject
    INavigator navigator;

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    IPresenterExplore presenterExplore;

    @Inject
    IPresenterLikes presenterLikes;

    @Inject
    ISettingsHelper settingsHelper;

    @Inject
    IRepositoryProfilePhotos repositoryProfilePhotos;

    private ListenerCacheSettings listenerCacheSettings;
    private INavigatorPagesListener listenerNavigatorPages;
    private ListenerCacheScroll listenerCacheScroll;
    private ListenerRepositoryProfilePhotos listenerRepositoryProfilePhotos;
    private WeakReference<IPresenterPagesContainerListener> refListener;

    public PresenterPagesContainer() {
        ApplicationRingoid.getComponent().inject(this);
        cacheScroll.setListener(listenerCacheScroll = new ListenerCacheScroll());
        navigatorPages.setLisener(listenerNavigatorPages = new ListenerNavigatorPages());
        cacheSettingsPrivacy.addListener(listenerCacheSettings = new ListenerCacheSettings());
    }

    @Override
    public void onViewCreate(FragmentManager childFragmentManager, int viewId) {
        navigatorPages.set(childFragmentManager, viewId);
        updateViewPrivacy();
        updateToolbar();
        updateView();
    }

    private void updateView() {
        navigatorPages.clearPage();
        repositoryProfilePhotos.setListener(listenerRepositoryProfilePhotos = new ListenerRepositoryProfilePhotos());
        repositoryProfilePhotos.request();
        navigationHide();
    }

    private void navigationHide() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().navigationHide();
    }

    private void updateToolbar() {
        cacheScroll.resetCache();

        if (refListener == null || refListener.get() == null) return;
        if (navigatorPages.isPageProfile())
            refListener.get().statusbarShow();
        else refListener.get().statusbarHide();
    }

    private void updateViewBottomSheet() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setBottomSheetDrawables(

                cacheSettingsPrivacy.isPrivacyPhotosOppositeSex()
                        ? R.drawable.ic_menu_profile_24dp
                        : cacheSettingsPrivacy.isPrivacyPhotosLikes()
                        ? R.drawable.ic_menu_profile_likes_24dp
                        : R.drawable.ic_menu_profile_noone_24dp,
                cacheLikes.isDataExist() ? R.drawable.ic_menu_favorite_red_24dp : R.drawable.ic_menu_favorite_24dp,
                cacheMessages.isDataExist() ? R.drawable.ic_menu_message_dot_24dp : R.drawable.ic_menu_message_24dp,
                R.drawable.ic_menu_explore_24dp
        );
    }

    private void updateViewPrivacy() {
        settingsHelper.requestGet();
    }

    @Override
    public void onClickPageLikes() {
        cacheScroll.resetCache();

        if (refListener != null && refListener.get() != null)
            refListener.get().statusbarHide();

        if (navigatorPages.isPageLikes()) {
            if (!presenterLikes.isPositionTop())
                presenterLikes.scrollTop();
            else viewDialogs.showDialogLikes();

            return;
        }

        navigatorPages.navigateLikes();
    }

    @Override
    public void onClickPageProfile() {
        cacheScroll.resetCache();

        if (refListener != null && refListener.get() != null)
            refListener.get().statusbarShow();

        navigatorPages.navigateProfile();
    }

    @Override
    public void onClickPageMessages() {
        cacheScroll.resetCache();

        if (refListener != null && refListener.get() != null)
            refListener.get().statusbarHide();

        navigatorPages.navigateMessages();
    }

    @Override
    public void onClickPageExplore() {
        cacheScroll.resetCache();

        if (refListener != null && refListener.get() != null)
            refListener.get().statusbarHide();

        if (navigatorPages.isPageExplore()) {
            if (!presenterExplore.isPositionTop())
                presenterExplore.scrollTop();
            else viewDialogs.showDialogExplore();

            return;
        }

        navigatorPages.navigateExplore();
    }

    @Override
    public void setListener(IPresenterPagesContainerListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private class ListenerCacheScroll implements ICacheScrollListener {
        @Override
        public void onScroll(int scrollSum, float alpha) {
            if (refListener == null || refListener.get() == null) return;

            refListener.get().setPosition(navigatorPages.isPageProfile(), -scrollSum, scrollSum, alpha);

        }

        @Override
        public void onScrollComplete(int scrollSum, int alpha) {
            if (refListener == null || refListener.get() == null) return;

            refListener.get().scrollComplete(navigatorPages.isPageProfile(), scrollSum, alpha);
        }
    }

    private class ListenerNavigatorPages implements INavigatorPagesListener {
        @Override
        public void onPageSelected(int num) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().setPageSelected(num);
        }
    }

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {

            updateViewBottomSheet();

            if (refListener == null || refListener.get() == null) return;
            int type = cacheSettingsPrivacy.getPrivacyPhotos();

            refListener.get().setStatusBarColor(type);
        }
    }

    private class ListenerRepositoryProfilePhotos implements IRepositoryProfilePhotosListener {
        @Override
        public void onSuccess() {
            if (refListener == null || refListener.get() == null) return;
            repositoryProfilePhotos.removeListener();
            refListener.get().navigationShow();

            navigatorPages.navigateCurrentPage();
        }

        @Override
        public void onError() {
        }

    }
}
