package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.view.INavigatorLikes;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.callback.IPresenterLikesContainerListener;
import com.ringoid.view.presenter.util.SimpleCacheInterfaceStateListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterLikesContainer implements IPresenterLikesContainer {

    @Inject
    INavigatorLikes navigatorLikes;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IPresenterFeedPage presenterFeedPage;

    private WeakReference<IPresenterLikesContainerListener> refListener;
    private ListenerCacheState listenerCacheState;

    public PresenterLikesContainer() {
        ApplicationRingoid.getComponent().inject(this);
        cacheInterfaceState.addListener(listenerCacheState = new ListenerCacheState());
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

    @Override
    public void scrollTop() {
        if (presenterFeedPage.isPositionTop()) return;
        presenterFeedPage.scrollTop();
        resetScrollPosition(cacheInterfaceState.getPageLikes());
    }

    @Override
    public void showPageLikes() {
        onClick(R.id.tvLikes);
    }

    @Override
    public void hidePageLikes() {
        refListener.get().setTabbarShown(false);
    }

    private void navigateCurrentPage() {
        if (navigatorLikes.isPageCurrent(cacheInterfaceState.getPageLikes())) {
            scrollTop();
            return;
        }
        navigatorLikes.navigatePage(cacheInterfaceState.getPageLikes());
        notifyCurrentPage();
    }

    private void resetScrollPosition(PAGE_ENUM pageLikes) {
        if (pageLikes == null) return;
        if (pageLikes.equals(PAGE_ENUM.LIKES_LIKES))
            cacheInterfaceState.resetCachePositionLikes();
        if (pageLikes.equals(PAGE_ENUM.LIKES_MESSAGES))
            cacheInterfaceState.resetCachePositionMessages();
        if (pageLikes.equals(PAGE_ENUM.LIKES_MATCHES))
            cacheInterfaceState.resetCachePositionMatches();
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

    private class ListenerCacheState extends SimpleCacheInterfaceStateListener {

        @Override
        public void onDialogComposeShowState(boolean isShown) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().setTabbarShown(!isShown);
        }
    }
}
