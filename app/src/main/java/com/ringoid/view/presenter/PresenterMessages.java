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
import com.ringoid.view.ui.util.IHelperMessageCompose;
import com.ringoid.view.ui.util.listener.IHelperMessageComposeListener;

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

    @Inject
    IHelperMessageCompose helperMessageCompose;

    private ListenerMessageCompose listenerMessageCompose;
    private ListenerCacheMessages listenerCacheMessages;
    private WeakReference<IPresenterMessagesListener> refListener;

    public PresenterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
        helperMessageCompose.addListener(listenerMessageCompose = new ListenerMessageCompose());
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
        }
    }

    private class ListenerMessageCompose implements IHelperMessageComposeListener {

        @Override
        public void scrollToPosition(String userSelectedID) {
            int position = cacheMessages.getPosition(userSelectedID, NO_VALUE);
            if (position == NO_VALUE) return;
            int offset = -(int) refContext.get().getResources().getDimension(R.dimen.divider);
            presenterFeedPage.scrollToPosition(position + 1, offset);
            cacheInterfaceState.setPositionScrollPageMessages(position + 1, offset);
        }
    }
}
