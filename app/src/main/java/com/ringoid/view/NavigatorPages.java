/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.view.ui.fragment.FragmentExplore;
import com.ringoid.view.ui.fragment.FragmentLikes;
import com.ringoid.view.ui.fragment.FragmentMessages;
import com.ringoid.view.ui.fragment.FragmentProfile;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class NavigatorPages implements INavigatorPages {

    private static final String CURRENT_FRAGMENT_TAB = "current_tab";

    @Inject
    ICacheInterfaceState cacheInterfaceState;

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
        cacheInterfaceState.setCurrentPage(1);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLikes(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateProfile() {
        cacheInterfaceState.setCurrentPage(0);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentProfile(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateMessages() {
        cacheInterfaceState.setCurrentPage(2);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMessages(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateExplore() {
        cacheInterfaceState.setCurrentPage(3);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentExplore(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateCurrentPage() {
        if (cacheInterfaceState.getCurrentPage() == 0) navigateProfile();
        if (cacheInterfaceState.getCurrentPage() == 1) navigateLikes();
        if (cacheInterfaceState.getCurrentPage() == 2) navigateMessages();
        if (cacheInterfaceState.getCurrentPage() == 3) navigateExplore();
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
        return fragment != null && fragment instanceof FragmentLikes;
    }

    @Override
    public boolean isPageProfile() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return false;

        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        return fragment != null && fragment instanceof FragmentProfile;
    }

    private void clearPage() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        if (fragment == null) return;
        refFragmentManager.get().beginTransaction().remove(fragment).commit();
    }
}
