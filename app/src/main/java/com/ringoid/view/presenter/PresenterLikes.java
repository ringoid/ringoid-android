/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.controller.data.repository.IRepositoryFeedLikes;
import com.ringoid.view.presenter.callback.IPresenterLikesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterLikes implements IPresenterLikes {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IRepositoryFeedLikes repositoryFeedLikes;

    @Inject
    ICacheProfile cacheProfile;

    private ListenerCacheLikes listenerCacheLikes;
    private WeakReference<IPresenterLikesListener> refListener;

    public PresenterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        cacheLikes.addListener(listenerCacheLikes = new ListenerCacheLikes());
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }

    @Override
    public void onCreateView() {
        cacheTutorial.resetLikesNum();
        if (!cacheProfile.isDataExist())
            showViewNoPhoto();
        else {
            scrollToSavedPosition();
            if (!cacheLikes.isDataExist()) {
                repositoryFeedLikes.request();
            }
        }
    }

    private void showViewNoPhoto() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showViewNoPhoto(R.string.message_no_photo_likes);
    }

    private void scrollToSavedPosition() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToPosition(cacheInterfaceState.getPositionScrollPageLikes());
    }

    @Override
    public void onScrollState(int newState, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheInterfaceState.setPositionScrollPageLikes(firstVisibleItemPosition);
        cacheScroll.onScrollIdle();
    }

    @Override
    public boolean isPositionTop() {
        if (refListener == null || refListener.get() == null) return true;
        return refListener.get().isPositionTop();
    }

    @Override
    public void scrollTop() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollTop();
    }

    @Override
    public void setListener(IPresenterLikesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onSwipeToRefresh() {
        repositoryFeedLikes.request();
    }

    private void hideRefreshLayout() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().completeRefresh();
    }

    private class ListenerCacheLikes implements ICacheLikesListener {
        @Override
        public void onUpdate() {
            hideRefreshLayout();
        }

        @Override
        public void onLiked(int adapterPosition, int itemPosition) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onLike(adapterPosition);
        }

        @Override
        public void onUnliked(int adapterPosition, int itemPosition) {

            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUnlike(adapterPosition);
        }
    }
}
