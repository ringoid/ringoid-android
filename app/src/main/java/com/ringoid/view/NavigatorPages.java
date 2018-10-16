/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.view.presenter.PresenterPagesContainer;
import com.ringoid.view.ui.fragment.FragmentExplore;
import com.ringoid.view.ui.fragment.FragmentLikes;
import com.ringoid.view.ui.fragment.FragmentMatches;
import com.ringoid.view.ui.fragment.FragmentMessages;
import com.ringoid.view.ui.fragment.FragmentProfile;
import com.ringoid.view.ui.fragment.FragmentSettings;
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

        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_LIKES);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLikes(), CURRENT_FRAGMENT_TAB)
                .commit();
    }


    @Override
    public void navigateProfile() {
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_PROFILE);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentProfile(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateMessages() {
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_MESSAGES);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMessages(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateExplore() {
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_EXPLORE);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentExplore(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateCurrentPage() {
        if (cacheInterfaceState.getCurrentPage() == PresenterPagesContainer.INDEX_PAGE_PROFILE)
            navigateProfile();
        if (cacheInterfaceState.getCurrentPage() == PresenterPagesContainer.INDEX_PAGE_MATCHES)
            navigateMatches();
        if (cacheInterfaceState.getCurrentPage() == PresenterPagesContainer.INDEX_PAGE_LIKES)
            navigateLikes();
        if (cacheInterfaceState.getCurrentPage() == PresenterPagesContainer.INDEX_PAGE_MESSAGES)
            navigateMessages();
        if (cacheInterfaceState.getCurrentPage() == PresenterPagesContainer.INDEX_PAGE_EXPLORE)
            navigateExplore();
        if (cacheInterfaceState.getCurrentPage() == PresenterPagesContainer.INDEX_PAGE_SETTINGS)
            navigateSettings();
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

    @Override
    public boolean isPageMessages() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return false;

        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        return fragment != null && fragment instanceof FragmentMessages;
    }

    @Override
    public void navigateMatches() {
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_MATCHES);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMatches(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void navigateSettings() {
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_SETTINGS);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettings(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public boolean isPageSettings() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return false;

        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        return fragment != null && fragment instanceof FragmentSettings;
    }

    private void clearPage() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_TAB);
        if (fragment == null) return;
        refFragmentManager.get().beginTransaction().remove(fragment).commit();
    }
}
