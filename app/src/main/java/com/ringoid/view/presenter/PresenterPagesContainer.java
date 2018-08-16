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
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;
import com.ringoid.view.INavigatorPagesListener;
import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;

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
    private INavigatorPagesListener listenerNavigatorPages;
    private ListenerCacheScroll listenerCacheScroll;
    private WeakReference<IPresenterPagesContainerListener> refListener;

    public PresenterPagesContainer() {
        ApplicationRingoid.getComponent().inject(this);
        cacheScroll.setListener(listenerCacheScroll = new ListenerCacheScroll());
        navigatorPages.setLisener(listenerNavigatorPages = new ListenerNavigatorPages());
    }

    @Override
    public void onViewCreate(FragmentManager childFragmentManager, int viewId) {
        navigatorPages.set(childFragmentManager, viewId);
        navigatorPages.navigateCurrentPage();
        updateViewPrivacy();
        updateViewBottomSheet();
    }

    private void updateViewBottomSheet() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setBottomSheetDrawables(R.drawable.ic_menu_profile_24dp,
                cacheLikes.isDataExist() ? R.drawable.ic_menu_favorite_red_24dp : R.drawable.ic_menu_favorite_24dp,
                cacheMessages.isDataExist() ? R.drawable.ic_menu_message_green_24dp : R.drawable.ic_menu_message_24dp,
                R.drawable.ic_menu_explore_24dp
        );
    }

    private void updateViewPrivacy() {
        if (refListener == null || refListener.get() == null) return;
        int type = cacheSettingsPrivacy.getPrivacyPhotos();

        refListener.get().setViewPrivacy(type == 0 ? R.drawable.ic_privacy_all_gray_32dp
                : type == 1 ? R.drawable.ic_privacy_likes_gray_32dp
                : R.drawable.ic_privacy_noone_gray_32dp);
    }

    @Override
    public void onClickWallet() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showDialogInvite();
    }

    @Override
    public void onClickPageLikes() {
        cacheScroll.resetCache();
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
        navigatorPages.navigateMessages();
    }

    @Override
    public void onClickPageExplore() {
        cacheScroll.resetCache();
        navigatorPages.navigateExplore();
    }

    @Override
    public void setListener(IPresenterPagesContainerListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickSettings() {
        navigator.navigateSettings();
    }

    @Override
    public void onClickPrivacy() {
        navigator.navigateSettingsPrivacy(true);
    }

    private class ListenerCacheScroll implements ICacheScrollListener {
        @Override
        public void onScroll(int scrollSum, float alpha) {
            if (refListener == null || refListener.get() == null) return;

            refListener.get().setPosition(-scrollSum, scrollSum, alpha);

        }
    }

    private class ListenerNavigatorPages implements INavigatorPagesListener {
        @Override
        public void onPageSelected(int num) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().setPageSelected(num, getBackgroundColorRes(num), getSubtitleColorRes(num));
        }

        private int getSubtitleColorRes(int num) {
            if (num == 1) return R.color.colorAccent;
            if (num == 2) return R.color.colorAccentGreen;
            return android.R.color.black;
        }

        private int getBackgroundColorRes(int num) {

            if (num == 1 && cacheLikes.isDataExist()) return R.color.bottomMenuColorAccent;
            if (num == 2 && cacheMessages.isDataExist()) return R.color.bottomMenuColorAccentGreen;

            return R.color.menu_bottom_selected;
        }
    }
}