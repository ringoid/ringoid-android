/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
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

    private static final int NO_VALUE = -1;
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

    @Inject
    WeakReference<Context> refContext;

    private ListenerCacheMessages listenerCacheMessages;
    private WeakReference<IPresenterMessagesListener> refListener;

    public PresenterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
    }

    @Override
    public void onScrollState(int newState, int firstVisibleItemPosition, int offset) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheScroll.onScrollIdle();
        cacheInterfaceState.setPositionScrollPageMessages(firstVisibleItemPosition, offset);
    }

    @Override
    public void setListener(IPresenterMessagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        if (presenterFeedPage.checkDataProfileExist(R.string.message_no_photo_messages)) {
            presenterFeedPage.scrollToPosition(cacheInterfaceState.getPositionScrollPageMessages(), cacheInterfaceState.getPositionScrollPageMessagesOffset());
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

        @Override
        public void onSelectedUserUpdate(String userSelectedID) {
            int position = cacheMessages.getPosition(userSelectedID, NO_VALUE);
            if (position == NO_VALUE) return;
            presenterFeedPage.scrollToPosition(position, position == 0 ? 0 : (int) refContext.get().getResources().getDimension(R.dimen.toolbar_height_with_statusbar));
        }
    }
}
