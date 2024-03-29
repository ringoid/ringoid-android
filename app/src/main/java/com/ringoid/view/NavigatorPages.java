/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.view.ui.fragment.FragmentExplore;
import com.ringoid.view.ui.fragment.FragmentLikesContainer;
import com.ringoid.view.ui.fragment.FragmentProfile;
import com.ringoid.view.ui.util.IHelperFullscreen;
import com.ringoid.view.ui.util.IHelperScreenshots;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class NavigatorPages implements INavigatorPages {

    private static final String CURRENT_FRAGMENT_TAB = "current_tab";

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IHelperScreenshots helperScreenshots;

    @Inject
    IHelperFullscreen helperFullscreen;

    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

    public NavigatorPages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void set(FragmentManager childFragmentManager, int viewId) {
        this.refFragmentManager = new WeakReference<>(childFragmentManager);
        this.viewId = viewId;
    }

    @Override
    public void navigateLikes() {

        cacheInterfaceState.setCurrentPage(PAGE_ENUM.FEED_LIKES);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLikesContainer(), CURRENT_FRAGMENT_TAB)
                .commit();
    }


    @Override
    public void navigateProfile() {
        cacheInterfaceState.setCurrentPage(PAGE_ENUM.FEED_PROFILE);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentProfile(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateExplore() {
        cacheInterfaceState.setCurrentPage(PAGE_ENUM.FEED_EXPLORE);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentExplore(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateCurrentPage() {
        if (cacheInterfaceState.getCurrentPage().equals(PAGE_ENUM.FEED_PROFILE))
            navigateProfile();
        if (cacheInterfaceState.getCurrentPage().equals(PAGE_ENUM.FEED_LIKES))
            navigateLikes();
        if (cacheInterfaceState.getCurrentPage().equals(PAGE_ENUM.FEED_EXPLORE))
            navigateExplore();
    }

    @Override
    public boolean isPageExplore() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return false;

        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        return fragment != null && fragment instanceof FragmentExplore;
    }

    @Override
    public boolean isPageLikes() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return false;

        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        return fragment != null && fragment instanceof FragmentLikesContainer;
    }

    @Override
    public boolean isPageProfile() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return false;

        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        return fragment != null && fragment instanceof FragmentProfile;
    }

    @Override
    public void navigateProfilePhotoAdd() {
        cacheInterfaceState.setCurrentPage(PAGE_ENUM.FEED_PROFILE);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentProfile.getInstancePhotoAdd(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    private void clearPage() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        if (fragment == null) return;
        refFragmentManager.get().beginTransaction().remove(fragment).commit();
    }
}
