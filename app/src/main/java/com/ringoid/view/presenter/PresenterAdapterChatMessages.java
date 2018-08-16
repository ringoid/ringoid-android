/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.CacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterChatMessages implements IPresenterAdapterChatMessages {

    private final CacheListener listenerCache;
    @Inject
    ICacheChatMessages cacheChatMessages;
    private WeakReference<IPresenterAdapterChatMessagesListener> refListener;

    public PresenterAdapterChatMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheChatMessages.addListener(listenerCache = new CacheListener());
    }

    @Override
    public int getItemsNum() {
        return cacheChatMessages.getDataSize();
    }

    @Override
    public boolean isMessageSelf(int position) {
        return cacheChatMessages.isSelf(position);
    }

    @Override
    public String getUrl(int position) {
        return cacheChatMessages.getUrl(position);
    }

    @Override
    public String getMessage(int position) {
        return cacheChatMessages.getMessage(position);
    }

    @Override
    public void setListener(IPresenterAdapterChatMessagesListener messagesListener) {
        this.refListener = new WeakReference<>(messagesListener);
    }

    @Override
    public boolean isMessageText(int position) {
        String message = getMessage(position);
        if (TextUtils.isEmpty(message)) return false;

        return !isMessageSmile(position);
    }

    @Override
    public boolean isMessageSmile(int position) {
        return getMessageSmile(position) != 0;
    }

    @Override
    public int getMessageSmile(int position) {
        String message = getMessage(position);

        if (message.contains(CacheChatMessages.SMILE_HEART))
            return R.drawable.ic_smile_heart;

        if (message.contains(CacheChatMessages.SMILE_SHY))
            return R.drawable.ic_smile_shy;

        if (message.contains(CacheChatMessages.SMILE_LOVE))
            return R.drawable.ic_smile_in_love;

        if (message.contains(CacheChatMessages.SMILE_KISS))
            return R.drawable.ic_smile_kiss;

        return 0;
    }

    private class CacheListener implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }
}
