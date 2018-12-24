/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    private View view;

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
    public void onCreateView(View view) {
        this.view = view;
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
        public void scrollToPosition(String userSelectedID, boolean isOpenChat) {
            final int position = cacheMessages.getPosition(userSelectedID, NO_VALUE);
            if (position == NO_VALUE) return;

            int offsetMarginTop = 0;
            if (!isOpenChat) {
                offsetMarginTop = (int) (refContext.get().getResources().getDimension(R.dimen.toolbar_height_with_statusbar));
            }

            final int offset = (position == 0
                    ? -(int) (refContext.get().getResources().getDimension(R.dimen.likesTitleHeight))
                    : -(int) refContext.get().getResources().getDimension(R.dimen.divider))+offsetMarginTop;

            view.post(new Runnable() {
                @Override
                public void run() {
                    presenterFeedPage.scrollToPosition(position, offset);
                    cacheInterfaceState.setPositionScrollPageMessages(position, offset);
                }
            });

        }
    }
}
