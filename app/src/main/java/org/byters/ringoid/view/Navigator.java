package org.byters.ringoid.view;

import android.support.v4.app.FragmentManager;

import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import org.byters.ringoid.view.ui.fragment.FragmentChat;
import org.byters.ringoid.view.ui.fragment.FragmentPages;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;
import org.byters.ringoid.view.ui.fragment.FragmentSettings;

import java.lang.ref.WeakReference;

public class Navigator implements INavigator {
    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

    @Override
    public void navigateFeed() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentPages())
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

    @Override
    public void navigateSettings() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettings())
                .commit();
    }

    @Override
    public void navigateBlacklistPhones() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentBlacklistPhones())
                .commit();
    }

    @Override
    public void navigateChat() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentChat())
                .commit();
    }
}
