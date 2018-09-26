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
    IPresenterMessages presenterMessages;

    @Inject
    ISettingsHelper settingsHelper;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    WeakReference<Context> refContext;

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

        if (navigatorPages.isPageProfile())
            navigator.statusbarShowFullscreen();
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

        navigator.statusbarShowFullscreen();

        navigatorPages.navigateProfile();
    }

    @Override
    public void onClickPageMessages() {
        cacheScroll.resetCache();

        if (navigatorPages.isPageMessages()) {
            if (!presenterMessages.isPositionTop())
                presenterMessages.scrollTop();
            return;
        }

        navigatorPages.navigateMessages();
    }

    @Override
    public void onClickPageExplore() {
        cacheScroll.resetCache();

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

    private void checkStatubar(boolean isDown) {
        if (isDown)
            navigator.statusbarHide();
        else
            navigator.statusbarShowFullscreen();
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
}
