/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
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
import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;
import com.ringoid.view.presenter.util.ISettingsHelper;
import com.ringoid.view.ui.util.IHelperFullscreen;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterPagesContainer implements IPresenterPagesContainer {

    private static final int INDEX_PAGE_PROFILE = 0;
    private static final int INDEX_PAGE_LIKES = 1;
    private static final int INDEX_PAGE_MATCHES = 2;
    private static final int INDEX_PAGE_EXPLORE = 3;

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
    ISettingsHelper settingsHelper;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    WeakReference<Context> refContext;

    @Inject
    IHelperFullscreen helperFullscreen;

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
    public void onViewCreate(FragmentManager childFragmentManager, int viewId) {
        navigatorPages.set(childFragmentManager, viewId);
        updateViewPrivacy();
        updateToolbar();
        navigatorPages.navigateCurrentPage();
    }

    private void updateToolbar() {
        cacheScroll.resetCache();

        navigatorPages.updateCurrentPage();
    }

    private void updateViewPrivacy() {
        settingsHelper.requestGet();
    }

    @Override
    public void onClickPageLikes() {
        cacheScroll.resetCache();

        if (navigatorPages.isPageLikes()) {
            if (!presenterFeedPage.isPositionTop()) {
                presenterFeedPage.scrollTop();
                cacheInterfaceState.resetCachePositionLikes();
            }
            return;
        }

        navigatorPages.navigateLikes();
    }

    @Override
    public void onClickPageProfile() {
        cacheScroll.resetCache();
        navigatorPages.navigateProfile();
    }

    @Override
    public void onClickPageMessages() {
        cacheScroll.resetCache();

        if (navigatorPages.isPageMessages()) {
            if (!presenterFeedPage.isPositionTop()) {
                presenterFeedPage.scrollTop();
                cacheInterfaceState.resetCachePositionMessage();
            }
            return;
        }

        navigatorPages.navigateMessages();
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


    /*
                getIconResLikes(false) cacheLikes.isDataExist() ?
                cacheMessages.isDataExist() ? R.drawable.ic_menu_message_dot_24dp : R.drawable.ic_menu_message_24dp,
                R.drawable.ic_menu_explore_24dp*/

    private int getIconResProfile(boolean isSelected) {
        return isSelected ? R.drawable.ic_menu_profile_white_24dp : R.drawable.ic_menu_profile_24dp;
    }

    private int getIconResLikes(boolean isSelected) {
        return isSelected
                ? (cacheLikes.isDataExist()
                ? R.drawable.ic_menu_favorite_white_dot_24dp
                : R.drawable.ic_menu_favorite_white_24dp)
                : (cacheLikes.isDataExist()
                ? R.drawable.ic_menu_favorite_dot_24dp
                : R.drawable.ic_menu_favorite_24dp);
    }

    private int getIconResMatches(boolean isSelected) {
        return isSelected
                ? (cacheMessages.isDataExist()
                ? R.drawable.ic_menu_message_white_dot_24dp : R.drawable.ic_menu_message_white_24dp)
                : cacheMessages.isDataExist()
                ? R.drawable.ic_menu_message_dot_24dp : R.drawable.ic_menu_message_24dp;
    }

    private int getIconResExplore(boolean isSelected) {
        return isSelected ? R.drawable.ic_menu_explore_white_24dp : R.drawable.ic_menu_explore_24dp;
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

    private class ListenerCacheInterfaceState implements ICacheInterfaceStateListener {
        @Override
        public void onPageSelected(int num) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().setPageSelected(num,
                    getIconResProfile(num == INDEX_PAGE_PROFILE),
                    getIconResLikes(num == INDEX_PAGE_LIKES),
                    getIconResMatches(num == INDEX_PAGE_MATCHES),
                    getIconResExplore(num == INDEX_PAGE_EXPLORE));
        }
    }

    private class ListenerCacheSettings implements ICacheSettingsPrivacyListener {
        @Override
        public void onUpdate() {
        }
    }
}
