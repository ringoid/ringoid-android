package org.byters.ringoid.view;

import android.support.v4.app.FragmentManager;

import java.lang.ref.WeakReference;

public class NavigatorPages implements INavigatorPages {

    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

    @Override
    public void set(FragmentManager childFragmentManager, int viewId) {
        this.refFragmentManager = new WeakReference<>(childFragmentManager);
        this.viewId = viewId;
    }

    @Override
    public void navigateRank() {

    }

    @Override
    public void navigateLikes() {

    }

    @Override
    public void navigateProfile() {

    }

    @Override
    public void navigateMessages() {

    }

    @Override
    public void navigateExplore() {

    }
}
