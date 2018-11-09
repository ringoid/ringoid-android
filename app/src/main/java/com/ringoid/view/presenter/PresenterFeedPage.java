package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
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
    private WeakReference<IPresenterFeedPageListener> refListenerView;

    public PresenterFeedPage() {
        ApplicationRingoid.getComponent().inject(this);
        cacheScroll.addListener(listenerCacheScroll = new ListenerCacheScroll());
    }

    @Override
    public void setListener(IPresenterFeedPageListener listener) {
        this.refListenerView = new WeakReference<>(listener);
    }

    @Override
    public void onScroll(int dy, int scrollSum) {
        cacheScroll.onScroll(dy, scrollSum);
    }

    @Override
    public boolean isPositionTop() {
        return refListenerView != null && refListenerView.get() != null && refListenerView.get().isPositionTop();
    }

    @Override
    public void scrollTop() {
        if (refListenerView == null || refListenerView.get() == null) return;
        refListenerView.get().scrollTop();
    }

    @Override
    public boolean checkDataProfileExist(int messageNoDataRes) {
        boolean isDataExist = cacheProfile.isDataExist();
        if (!isDataExist) showViewNoPhoto(messageNoDataRes);
        return isDataExist;
    }

    @Override
    public void scrollToPosition(int position, int offset) {
        if (refListenerView == null || refListenerView.get() == null) return;
        refListenerView.get().scrollToPosition(position, offset);
    }

    private void showViewNoPhoto(int messageNoDataRes) {
        if (refListenerView == null || refListenerView.get() == null) return;
        refListenerView.get().showViewNoPhoto(messageNoDataRes);
    }

    @Override
    public void hideRefreshLayout() {
        if (refListenerView == null || refListenerView.get() == null) return;
        refListenerView.get().completeRefresh();
    }

    private class ListenerCacheScroll implements ICacheScrollListener {

        @Override
        public void onScroll(boolean isDown, int scrollSum) {

        }

        @Override
        public void onScrollComplete(int scrollSum, int maxScroll, boolean isDown) {
            if (scrollSum >= maxScroll || scrollSum == 0) return;

            if (refListenerView == null || refListenerView.get() == null) return;
            refListenerView.get().scrollSmoothBy(isDown ? maxScroll - scrollSum : -scrollSum);
        }
    }
}
