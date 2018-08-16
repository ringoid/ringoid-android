/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.CacheChatMessages;
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
        refListener.get().setSendEnabled(cacheChatMessages.isSendEnabled());
    }

    @Override
    public void setListener(IPresenterChatListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickSmileShy() {
        cacheChatMessages.addMessage(CacheChatMessages.SMILE_SHY);
    }

    @Override
    public void onClickSmileLove() {
        cacheChatMessages.addMessage(CacheChatMessages.SMILE_LOVE);
    }

    @Override
    public void onClickSmileKiss() {
        cacheChatMessages.addMessage(CacheChatMessages.SMILE_KISS);
    }

    @Override
    public void onClickSmileHeart() {
        cacheChatMessages.addMessage(CacheChatMessages.SMILE_HEART);
    }

    @Override
    public void onClickSend(String message) {
        if (TextUtils.isEmpty(message)) return;
        cacheChatMessages.addMessage(message);
    }

    private class ListenerCacheChatMessages implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            setView();
        }
    }
}
