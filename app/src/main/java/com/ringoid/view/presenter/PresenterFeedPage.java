package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.listener.ICacheScrollListener;
import com.ringoid.view.presenter.callback.IPresenterFeedPageListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterFeedPage implements IPresenterFeedPage {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheProfile cacheProfile;

    private ListenerCacheScroll listenerCacheScroll;
    private WeakReference<IPresenterFeedPageListener> refListener;

    public PresenterFeedPage() {
        ApplicationRingoid.getComponent().inject(this);
        cacheScroll.addListener(listenerCacheScroll = new ListenerCacheScroll());
    }

    @Override
    public void setListener(IPresenterFeedPageListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onScroll(int dy, int scrollSum) {
        cacheScroll.onScroll(dy, scrollSum);
    }

    @Override
    public boolean isPositionTop() {
        return refListener != null && refListener.get() != null && refListener.get().isPositionTop();
    }

    @Override
    public void scrollTop() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollTop();
    }

    @Override
    public boolean checkDataProfileExist(int messageNoDataRes) {
        boolean isDataExist = cacheProfile.isDataExist();
        if (!isDataExist) showViewNoPhoto(messageNoDataRes);
        return isDataExist;
    }

    @Override
    public void scrollToPosition(int position) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToPosition(position);
    }

    private void showViewNoPhoto(int messageNoDataRes) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showViewNoPhoto(messageNoDataRes);
    }

    @Override
    public void hideRefreshLayout() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().completeRefresh();
    }

    private class ListenerCacheScroll implements ICacheScrollListener {

        @Override
        public void onScroll(boolean isDown, int scrollSum) {

        }

        @Override
        public void onScrollComplete(int scrollSum, int maxScroll, boolean isDown) {
            if (scrollSum >= maxScroll || scrollSum == 0) return;

            if (refListener == null || refListener.get() == null) return;
            refListener.get().scrollSmoothBy(isDown ? maxScroll - scrollSum : -scrollSum);
        }
    }
}
