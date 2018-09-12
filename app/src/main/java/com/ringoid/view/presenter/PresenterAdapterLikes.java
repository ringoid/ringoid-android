/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterAdapterLikesListener;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterLikes implements IPresenterAdapterLikes {

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    IViewPopup viewPopup;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    INavigator navigator;

    @Inject
    IViewDialogs viewDialogs;

    private ListenerDialogChatCompose listenerDialogChatCompose;
    private ListenerCacheLikes listenerCacheLikes;
    private WeakReference<IPresenterAdapterLikesListener> refListener;

    public PresenterAdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        cacheLikes.addListener(listenerCacheLikes = new ListenerCacheLikes());
    }

    @Override
    public int getItemsNum() {
        return cacheLikes.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheLikes.getItemsNum(position);
    }

    @Override
    public void onClickScrolls() {

    }

    @Override
    public void setListener(IPresenterAdapterLikesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public boolean isChatEmpty(int position) {
        return !cacheLikes.isMessagesExist(position);
    }

    @Override
    public void onClickChat(int position) {
        boolean isChatEmpty = isChatEmpty(position);

        cacheMessages.setUserSelected(position);

        if (isChatEmpty) {
            viewDialogs.showDialogChatCompose(listenerDialogChatCompose = new ListenerDialogChatCompose(cacheLikes.getUserId(position)));
            return;
        }

        navigator.navigateChat();
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        return cacheLikes.isLikedAnyPhoto(position);
    }

    private class ListenerCacheLikes implements ICacheLikesListener {
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
            cacheLikes.setMessagesExist(userId);
        }
    }
}
