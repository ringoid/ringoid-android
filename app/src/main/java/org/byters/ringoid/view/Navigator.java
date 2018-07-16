package org.byters.ringoid.view;

import android.support.v4.app.FragmentManager;

import org.byters.ringoid.view.ui.fragment.FragmentFeed;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;

import java.lang.ref.WeakReference;

public class Navigator implements INavigator {
    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

    @Override
    public void navigateFeed() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentFeed())
                .commit();
    }

    @Override
    public void navigateLogin() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLogin())
                .commit();
    }

    @Override
    public void set(FragmentManager supportFragmentManager, int viewId) {
        this.refFragmentManager = new WeakReference<>(supportFragmentManager);
        this.viewId = viewId;
    }
}
