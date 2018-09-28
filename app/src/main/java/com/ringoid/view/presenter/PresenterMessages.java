/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.controller.data.repository.IRepositoryFeedMessages;
import com.ringoid.view.presenter.callback.IPresenterMessagesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterMessages implements IPresenterMessages {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    IRepositoryFeedMessages repositoryFeedMessages;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    IPresenterFeedPage presenterFeedPage;

    private ListenerCacheMessages listenerCacheMessages;
    private WeakReference<IPresenterMessagesListener> refListener;

    public PresenterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
    }

    @Override
    public void onScrollState(int newState, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheScroll.onScrollIdle();
        cacheInterfaceState.setPositionScrollPageMessages(firstVisibleItemPosition);
    }

    @Override
    public void setListener(IPresenterMessagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        if (presenterFeedPage.checkDataProfileExist(R.string.message_no_photo_messages)) {
            presenterFeedPage.scrollToPosition(cacheInterfaceState.getPositionScrollPageMessages());
            if (!cacheMessages.isDataExist())
                repositoryFeedMessages.request();
        }
    }

    @Override
    public void onSwipeRefresh() {
        repositoryFeedMessages.request();
    }

    private class ListenerCacheMessages implements ICacheMessagesListener {
        @Override
        public void onUpdate() {
            presenterFeedPage.hideRefreshLayout();
        }
    }
}
