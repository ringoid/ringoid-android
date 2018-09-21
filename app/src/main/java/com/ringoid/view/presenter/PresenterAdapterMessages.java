/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterAdapterMessagesListener;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterMessages implements IPresenterAdapterMessages {

    @Inject
    INavigator navigator;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    IViewPopup viewPopup;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    ICacheChatMessages cacheChatMessages;

    private ListenerCacheChatMessages listenerCacheChatMessages;
    private ICacheMessagesListener listenerCacheMessages;
    private WeakReference<IPresenterAdapterMessagesListener> refListener;
    private ListenerDialogChatCompose listenerDialogChatCompose;

    public PresenterAdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
        cacheChatMessages.addListener(listenerCacheChatMessages = new ListenerCacheChatMessages());
    }

    @Override
    public int getItemsNum() {
        return cacheMessages.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheMessages.getItemsNum(position);
    }

    @Override
    public boolean isMessagesNew(int position) {
        return cacheChatMessages.isMessageNew(cacheMessages.getUserId(position));
    }

    @Override
    public boolean isMessagesExist(int position) {
        return cacheChatMessages.isDataExist(cacheMessages.getUserId(position));
    }

    @Override
    public void setListener(IPresenterAdapterMessagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public boolean isChatEmpty(int position) {
        return !cacheChatMessages.isDataExist(cacheMessages.getUserId(position));
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        return cacheMessages.isLikedAnyPhoto(position);
    }

    @Override
    public void onClickChat(int position) {
        boolean isChatEmpty = isChatEmpty(position);

        cacheMessages.setUserSelected(position);

        if (isChatEmpty) {
            viewDialogs.showDialogChatCompose(listenerDialogChatCompose = new ListenerDialogChatCompose(cacheMessages.getUserId(position)));
            return;
        }

        navigator.navigateChat();
    }

    private class ListenerCacheMessages implements ICacheMessagesListener {
        @Override
        public void onUpdate() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }

    private class ListenerDialogChatCompose implements IDialogChatComposeListener {
        private String userId;

        ListenerDialogChatCompose(String userId) {
            this.userId = userId;
        }

        @Override
        public void onSend(String message) {
            viewPopup.showToast(R.string.message_sent);
            cacheChatMessages.addMessage(userId, message);
        }
    }

    private class ListenerCacheChatMessages implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }

    @Override
    public void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheMessages.setSelected(adapterPosition, firstVisibleItemPosition);
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return cacheMessages.getSelectedPhotoPosition(position);
    }
}
