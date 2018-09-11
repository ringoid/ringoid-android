/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.view.presenter.callback.IPresenterChatListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterChat implements IPresenterChat {

    private final ICacheChatMessagesListener listenerCacheChatMessages;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheChatMessages cacheChatMessages;

    private WeakReference<IPresenterChatListener> refListener;

    public PresenterChat() {
        ApplicationRingoid.getComponent().inject(this);
        cacheChatMessages.addListener(listenerCacheChatMessages = new ListenerCacheChatMessages());
    }

    @Override
    public void onCreateView() {
        cacheChatMessages.resetCache();
        setView();
    }

    private void setView() {
        if (refListener == null || refListener.get() == null) return;

        refListener.get().setImage("file:///android_asset/" + cacheMessages.getUrlSelectedUser());
        refListener.get().setDataExist(cacheChatMessages.isDataExist());
    }

    @Override
    public void setListener(IPresenterChatListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickSend(String message) {
        if (TextUtils.isEmpty(message)) return;
        cacheChatMessages.addMessage(message);
    }

    @Override
    public void onClickClear() {
        cacheChatMessages.resetCache();
    }

    private class ListenerCacheChatMessages implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            setView();
        }
    }
}
