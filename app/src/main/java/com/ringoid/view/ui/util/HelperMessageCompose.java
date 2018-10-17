package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.util.listener.IHelperMessageComposeListener;

import java.util.WeakHashMap;

import javax.inject.Inject;

public class HelperMessageCompose implements IHelperMessageCompose {


    @Inject
    IViewPopup viewPopup;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    INavigator navigator;

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    IHelperMessageSend helperMessageSend;

    @Inject
    ICacheMessages cacheMessages;

    private ListenerDialogChatCompose listenerDialogChatCompose;
    private WeakHashMap<String, IHelperMessageComposeListener> listeners;

    public HelperMessageCompose() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClick(String userId) {
        boolean isChatEmpty = !cacheChatMessages.isDataExist(userId);

        if (isChatEmpty) {
            notifyListeners(userId);
            viewDialogs.showDialogChatCompose(listenerDialogChatCompose = new ListenerDialogChatCompose(userId));
            return;
        }

        cacheMessages.setUserSelected(userId);
        navigator.navigateChat();
    }

    @Override
    public void addListener(IHelperMessageComposeListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    private void notifyListeners(String userId) {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            IHelperMessageComposeListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.scrollToPosition(userId);
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
            helperMessageSend.sendMessage(userId, message);
        }
    }
}
