/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.data.memorycache.listener.ICacheScrollListener;
import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;
import com.ringoid.view.presenter.util.ISettingsHelper;
import com.ringoid.view.presenter.util.SimpleCacheInterfaceStateListener;
import com.ringoid.view.ui.util.IHelperFullscreen;
import com.ringoid.view.ui.util.IHelperTheme;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterPagesContainer implements IPresenterPagesContainer {

    public static final int ACTION_PHOTO_ADD = 1;

    public static final int INDEX_PAGE_EXPLORE = 0;
    public static final int INDEX_PAGE_LIKES = 1;
    public static final int INDEX_PAGE_PROFILE = 2;

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
    IPresenterFeedPage presenterFeedPage;

    @Inject
    IPresenterLikesContainer presenterLikesContainer;

    @Inject
    IPresenterSettings presenterSettings;

    @Inject
    ISettingsHelper settingsHelper;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    WeakReference<Context> refContext;

    @Inject
    IHelperFullscreen helperFullscreen;

    @Inject
    IHelperTheme helperTheme;

    private ListenerCacheSettings listenerCacheSettings;
    private ICacheInterfaceStateListener listenerCacheInterfaceState;
    private ListenerCacheScroll listenerCacheScroll;
    private WeakReference<IPresenterPagesContainerListener> refListener;

    public PresenterPagesContainer() {
        ApplicationRingoid.getComponent().inject(this);
        cacheScroll.addListener(listenerCacheScroll = new ListenerCacheScroll());
        cacheInterfaceState.addListener(listenerCacheInterfaceState = new ListenerCacheInterfaceState());
        cacheSettingsPrivacy.addListener(listenerCacheSettings = new ListenerCacheSettings());
    }

    @Override
    public void onViewCreate(FragmentManager childFragmentManager, int viewId, int action) {
        navigatorPages.set(childFragmentManager, viewId);
        updateViewPrivacy();
        updateToolbar();

        navigate(action);
    }

    private void navigate(Integer action) {
        if (action == ACTION_PHOTO_ADD) {
            navigatorPages.navigateProfilePhotoAdd();
            return;
        }
        navigatorPages.navigateCurrentPage();
    }

    private void updateToolbar() {
        cacheScroll.resetCache();
    }

    private void updateViewPrivacy() {
        settingsHelper.requestGet();
    }

    @Override
    public void onClickPageLikes() {
        cacheScroll.resetCache();

        if (navigatorPages.isPageLikes()) {
            if (!presenterFeedPage.isPositionTop())
                presenterLikesContainer.scrollTop();
            else
                presenterLikesContainer.showPageLikes();
            return;
        }

        navigatorPages.navigateLikes();
    }

    @Override
    public void onClickPageProfile() {
        if (navigatorPages.isPageProfile()) {
            cacheInterfaceState.resetPhotoSelected();
            return;
        }

        cacheScroll.resetCache();
        navigatorPages.navigateProfile();
    }

    @Override
    public void onClickPageExplore() {
        cacheScroll.resetCache();

        if (navigatorPages.isPageExplore()) {
            if (!presenterFeedPage.isPositionTop()) {
                presenterFeedPage.scrollTop();
                cacheInterfaceState.resetCachePositionExplore();
            }
            return;
        }

        navigatorPages.navigateExplore();
    }

    @Override
    public void setListener(IPresenterPagesContainerListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void checkStatubar(boolean isDown) {
        if (isDown)
            helperFullscreen.statusbarHide();
        else
            helperFullscreen.statusbarShowFullscreen();
    }

    private int getIconResProfile(boolean isSelected) {
        return isSelected ? helperTheme.getDrawableMenuProfileActive() : helperTheme.getDrawableMenuProfile();
    }

    private int getIconResLikes(boolean isSelected) {
        return isSelected ? helperTheme.getDrawableMenuLikesActive() : helperTheme.getDrawableMenuLikes();
    }

    private int getIconResExplore(boolean isSelected) {
        return isSelected ? helperTheme.getDrawableMenuExploreActive() : helperTheme.getDrawableMenuExplore();
    }

    private int getIndex(PAGE_ENUM num) {
        return num.equals(PAGE_ENUM.FEED_PROFILE)
                ? INDEX_PAGE_PROFILE
                : num.equals(PAGE_ENUM.FEED_LIKES)
                ? INDEX_PAGE_LIKES
                : INDEX_PAGE_EXPLORE;
    }

    private class ListenerCacheScroll implements ICacheScrollListener {
        @Override
        public void onScroll(boolean isDown, int position) {
            checkStatubar(isDown);

            if (refListener == null || refListener.get() == null) return;
            refListener.get().setPosition(position);
        }

        @Override
        public void onScrollComplete(int scrollSum, int maxScroll, boolean isDown) {
        }
    }

    private class ListenerCacheInterfaceState extends SimpleCacheInterfaceStateListener {
        @Override
        public void onPageSelected(PAGE_ENUM num) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().setPageSelected(getIndex(num),
                    getIconResProfile(num.equals(PAGE_ENUM.FEED_PROFILE)),
                    getIconResLikes(num.equals(PAGE_ENUM.FEED_LIKES)),
                    getIconResExplore(num.equals(PAGE_ENUM.FEED_EXPLORE)));
        }
    }

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {
        }
    }
}
