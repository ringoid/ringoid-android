/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheMessages;
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

    private ICacheMessagesListener listenerCacheMessages;

    private WeakReference<IPresenterAdapterMessagesListener> refListener;

    private ListenerDialogChatCompose listenerDialogChatCompose;

    public PresenterAdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
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
        return position == 1;
    }

    @Override
    public boolean isMessagesExist(int position) {
        return position == 2;
    }

    @Override
    public void setListener(IPresenterAdapterMessagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public boolean isChatEmpty(int position) {
        return !cacheMessages.isMessagesExist(position);
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
            cacheMessages.setMessagesExist(userId);
        }
    }
}
