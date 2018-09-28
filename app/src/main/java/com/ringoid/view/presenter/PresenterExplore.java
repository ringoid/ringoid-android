/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.listener.ICacheExploreListener;
import com.ringoid.controller.data.repository.IRepositoryFeedExplore;
import com.ringoid.view.presenter.callback.IPresenterExploreListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterExplore implements IPresenterExplore {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IRepositoryFeedExplore repositoryFeedExplore;

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    IPresenterFeedPage presenterFeedPage;

    private ListenerCacheExplore listenerCacheExplore;
    private WeakReference<IPresenterExploreListener> refListener;

    public PresenterExplore() {
        ApplicationRingoid.getComponent().inject(this);
        cacheExplore.addListener(listenerCacheExplore = new ListenerCacheExplore());
    }

    @Override
    public void onCreateView() {
        cacheTutorial.resetLikesNum();

        if (presenterFeedPage.checkDataProfileExist(R.string.message_no_photo_explore)) {
            presenterFeedPage.scrollToPosition(cacheInterfaceState.getPositionScrollPageExplore());
            if (!cacheExplore.isDataExist())
                repositoryFeedExplore.request();
        }
    }

    @Override
    public void onScrollState(int newState, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheScroll.onScrollIdle();
        cacheInterfaceState.setPositionScrollPageExplore(firstVisibleItemPosition);
    }

    @Override
    public void setListener(IPresenterExploreListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onSwipeRefresh() {
        repositoryFeedExplore.request();
    }

    private class ListenerCacheExplore implements ICacheExploreListener {
        @Override
        public void onUpdate() {
            presenterFeedPage.hideRefreshLayout();
        }
    }
}
