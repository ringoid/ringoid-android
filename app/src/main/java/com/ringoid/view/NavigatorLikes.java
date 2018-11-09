package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v4.app.FragmentManager;

import com.ringoid.view.ui.fragment.FragmentLikes;
import com.ringoid.view.ui.fragment.FragmentMatches;
import com.ringoid.view.ui.fragment.FragmentMessages;

import java.lang.ref.WeakReference;

public class NavigatorLikes implements INavigatorLikes {
    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

    private String CURRENT_FRAGMENT_TAB = "current_tab_likes";

    @Override
    public void navigatePage(PAGE_ENUM pageLikes) {
        if (pageLikes == null || pageLikes.equals(PAGE_ENUM.LIKES_LIKES))
            navigateLikes();
        else if (pageLikes.equals(PAGE_ENUM.LIKES_MATCHES))
            navigateMatches();
        else navigateMessages();
    }

    private void navigateMessages() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMessages(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    private void navigateMatches() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentMatches(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    private void navigateLikes() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLikes(), CURRENT_FRAGMENT_TAB)
                .commit();
    }

    @Override
    public void set(FragmentManager childFragmentManager, int vContent) {
        this.refFragmentManager = new WeakReference<>(childFragmentManager);
        this.viewId = vContent;
    }
}
