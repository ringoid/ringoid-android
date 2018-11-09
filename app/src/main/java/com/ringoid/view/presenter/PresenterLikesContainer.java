package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.view.INavigatorLikes;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.callback.IPresenterLikesContainerListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterLikesContainer implements IPresenterLikesContainer {

    @Inject
    INavigatorLikes navigatorLikes;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private WeakReference<IPresenterLikesContainerListener> refListener;

    public PresenterLikesContainer() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClick(int id) {
        if (id == R.id.tvLikes) {
            cacheInterfaceState.setPageLikes(PAGE_ENUM.LIKES_LIKES);
        }

        if (id == R.id.tvMatches) {
            cacheInterfaceState.setPageLikes(PAGE_ENUM.LIKES_MATCHES);
        }

        if (id == R.id.tvMessages) {
            cacheInterfaceState.setPageLikes(PAGE_ENUM.LIKES_MESSAGES);
        }

        navigateCurrentPage();
    }

    @Override
    public void onCreateView(FragmentManager childFragmentManager, int vContent) {
        navigatorLikes.set(childFragmentManager, vContent);
        navigateCurrentPage();
    }

    @Override
    public void setListener(IPresenterLikesContainerListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void navigateCurrentPage() {
        navigatorLikes.navigatePage(cacheInterfaceState.getPageLikes());
        notifyCurrentPage();
    }

    private void notifyCurrentPage() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPageSelected(getCurrentPageIndex());
    }

    private int getCurrentPageIndex() {
        if (cacheInterfaceState.getPageLikes() == null) return 0;
        return cacheInterfaceState.getPageLikes().equals(PAGE_ENUM.LIKES_LIKES)
                ? 0
                : cacheInterfaceState.getPageLikes().equals(PAGE_ENUM.LIKES_MATCHES)
                ? 1
                : 2;
    }
}
