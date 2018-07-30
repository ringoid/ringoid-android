package org.byters.ringoid.view.presenter;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.controller.data.memorycache.CacheChatMessages;
import org.byters.ringoid.controller.data.memorycache.ICacheChatMessages;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import org.byters.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;

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

        if (message.contains(CacheChatMessages.SMILE_WINK))
            return R.drawable.ic_smile_wink;

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
