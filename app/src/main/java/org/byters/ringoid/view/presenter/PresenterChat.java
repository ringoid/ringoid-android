package org.byters.ringoid.view.presenter;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.CacheChatMessages;
import org.byters.ringoid.controller.data.memorycache.ICacheChatMessages;
import org.byters.ringoid.controller.data.memorycache.ICacheMessages;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import org.byters.ringoid.view.presenter.callback.IPresenterChatListener;

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
    public void onClickSmileWink() {
        cacheChatMessages.addMessage(CacheChatMessages.SMILE_WINK);
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