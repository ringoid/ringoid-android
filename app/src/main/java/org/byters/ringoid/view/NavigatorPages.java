package org.byters.ringoid.view;

import android.support.v4.app.FragmentManager;

import org.byters.ringoid.view.ui.fragment.FragmentExplore;
import org.byters.ringoid.view.ui.fragment.FragmentLikes;
import org.byters.ringoid.view.ui.fragment.FragmentMessages;
import org.byters.ringoid.view.ui.fragment.FragmentProfile;
import org.byters.ringoid.view.ui.fragment.FragmentRank;

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
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentRank())
                .commit();
    }

    @Override
    public void navigateLikes() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLikes())
                .commit();
    }

    @Override
    public void navigateProfile() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentProfile())
                .commit();
    }

    @Override
    public void navigateMessages() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMessages())
                .commit();
    }

    @Override
    public void navigateExplore() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentExplore())
                .commit();
    }
}
