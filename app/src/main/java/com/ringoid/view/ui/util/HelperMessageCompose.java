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
    ICacheMessages cacheMessages;

    @Inject
    IHelperMessageSend helperMessageSend;

    private ListenerDialogChatCompose listenerDialogChatCompose;

    public HelperMessageCompose() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClick(String userId) {
        boolean isChatEmpty = !cacheChatMessages.isDataExist(userId);

        cacheMessages.setUserSelected(userId);

        if (isChatEmpty) {
            viewDialogs.showDialogChatCompose(listenerDialogChatCompose = new ListenerDialogChatCompose(userId));
            return;
        }

        navigator.navigateChat();
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
