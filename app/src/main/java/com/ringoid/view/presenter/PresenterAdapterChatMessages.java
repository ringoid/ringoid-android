/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;
import com.ringoid.view.ui.view.utils.ClipboardUtils;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterChatMessages implements IPresenterAdapterChatMessages {

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    IViewPopup viewPopup;

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

    @Override
    public void onLongClick(Context context, int adapterPosition) {
        String message = getMessage(adapterPosition);
        ClipboardUtils.copyToClipboard(context, context.getString(R.string.message_clipboard_chat_message), message);
        viewPopup.showToast(R.string.message_copy_to_clipboard);
    }

    private class CacheListener implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }
}
