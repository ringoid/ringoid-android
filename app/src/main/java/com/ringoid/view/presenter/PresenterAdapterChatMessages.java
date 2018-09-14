/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterChatMessages implements IPresenterAdapterChatMessages {

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    ICacheMessages cacheMessages;

    private CacheListener listenerCache;
    private WeakReference<IPresenterAdapterChatMessagesListener> refListener;

    public PresenterAdapterChatMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheChatMessages.addListener(listenerCache = new CacheListener());
    }

    @Override
    public int getItemsNum() {
        return cacheChatMessages.getDataSize(cacheMessages.getUserSelectedID());
    }

    @Override
    public boolean isMessageSelf(int position) {
        return cacheChatMessages.isSelf(cacheMessages.getUserSelectedID(), position);
    }

    @Override
    public String getMessage(int position) {
        return cacheChatMessages.getMessage(cacheMessages.getUserSelectedID(), position);
    }

    @Override
    public void setListener(IPresenterAdapterChatMessagesListener messagesListener) {
        this.refListener = new WeakReference<>(messagesListener);
    }

    private class CacheListener implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }
}
