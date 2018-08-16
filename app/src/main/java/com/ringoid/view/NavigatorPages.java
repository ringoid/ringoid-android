/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.support.v4.app.FragmentManager;

import com.ringoid.view.ui.fragment.FragmentExplore;
import com.ringoid.view.ui.fragment.FragmentLikes;
import com.ringoid.view.ui.fragment.FragmentMessages;
import com.ringoid.view.ui.fragment.FragmentProfile;

import java.lang.ref.WeakReference;

public class NavigatorPages implements INavigatorPages {

    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;
    private WeakReference<INavigatorPagesListener> refListener;
    private int currentPage;

    public NavigatorPages() {
        currentPage = 3;
    }

    @Override
    public void set(FragmentManager childFragmentManager, int viewId) {
        this.refFragmentManager = new WeakReference<>(childFragmentManager);
        this.viewId = viewId;
    }

    @Override
    public void navigateLikes() {
        currentPage = 1;
        notifyListeners(1);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLikes())
                .commit();
    }

    private void notifyListeners(int num) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onPageSelected(num);
    }

    @Override
    public void navigateProfile() {
        currentPage = 0;
        notifyListeners(0);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentProfile())
                .commit();
    }

    @Override
    public void navigateMessages() {
        currentPage = 2;
        notifyListeners(2);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMessages())
                .commit();
    }

    @Override
    public void navigateExplore() {
        currentPage = 3;
        notifyListeners(3);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentExplore())
                .commit();
    }

    @Override
    public void setLisener(INavigatorPagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void navigateCurrentPage() {
        if (currentPage == 0) navigateProfile();
        if (currentPage == 1) navigateLikes();
        if (currentPage == 2) navigateMessages();
        if (currentPage == 3) navigateExplore();
    }
}
