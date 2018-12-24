/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.controller.data.repository.IRepositoryFeedLikes;
import com.ringoid.view.presenter.callback.IPresenterLikesListener;
import com.ringoid.view.ui.util.IHelperMessageCompose;
import com.ringoid.view.ui.util.listener.IHelperMessageComposeListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterLikes implements IPresenterLikes {

    private static final int NO_VALUE = -1;

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IRepositoryFeedLikes repositoryFeedLikes;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    IPresenterFeedPage presenterFeedPage;

    @Inject
    WeakReference<Context> refContext;

    @Inject
    IHelperMessageCompose helperMessageCompose;

    private ListenerMessageCompose listenerMessageCompose;
    private ListenerCacheMessages listenerCacheMessages;
    private ListenerCacheLikes listenerCacheLikes;
    private WeakReference<IPresenterLikesListener> refListener;

    public PresenterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        cacheLikes.addListener(listenerCacheLikes = new ListenerCacheLikes());
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
        helperMessageCompose.addListener(listenerMessageCompose = new ListenerMessageCompose());
    }

    @Override
    public void onCreateView() {
        cacheTutorial.resetLikesNum();
        if (presenterFeedPage.checkDataProfileExist(R.string.message_no_photo_likes)) {
            presenterFeedPage.scrollToPosition(cacheInterfaceState.getPositionScrollPageLikes(), cacheInterfaceState.getPositionScrollPageLikesOffset());
            if (!cacheLikes.isDataExist()) {
                repositoryFeedLikes.request();
            }
        }
    }

    @Override
    public void onScrollState(int newState, int firstVisibleItemPosition, int offset) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheInterfaceState.setPositionScrollPageLikes(firstVisibleItemPosition, offset);
        cacheScroll.onScrollIdle();
    }

    @Override
    public void setListener(IPresenterLikesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onSwipeToRefresh() {
        repositoryFeedLikes.request();
    }

    private class ListenerCacheLikes implements ICacheLikesListener {
        @Override
        public void onUpdate() {
            presenterFeedPage.hideRefreshLayout();
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

    private class ListenerCacheMessages implements ICacheMessagesListener {
        @Override
        public void onUpdate() {

        }

        @Override
        public void onSelectedUserUpdate(String userSelectedID) {
        }
    }

    private class ListenerMessageCompose implements IHelperMessageComposeListener {

        @Override
        public void scrollToPosition(String userSelectedID, boolean isOpenChat) {

            int position = cacheLikes.getPosition(userSelectedID, NO_VALUE);
            if (position == NO_VALUE) return;
            int offset = position == 0
                    ? -(int) (refContext.get().getResources().getDimension(R.dimen.likesTitleHeight))
                    : -(int)  refContext.get().getResources().getDimension(R.dimen.divider);
            presenterFeedPage.scrollToPosition(position, offset);
            cacheInterfaceState.setPositionScrollPageLikes(position, offset);
        }
    }
}
