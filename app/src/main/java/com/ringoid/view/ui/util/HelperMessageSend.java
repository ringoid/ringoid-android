package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;

import javax.inject.Inject;

public class HelperMessageSend implements IHelperMessageSend {

    private static final int MESSAGE_LENGTH_MAX = 1000;

    @Inject
    ICacheChatMessages cacheChatMessages;

    public HelperMessageSend() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void sendMessage(String userId, String message) {
        message = message.trim();

        if (TextUtils.isEmpty(message)) return;

        if (message.length() > MESSAGE_LENGTH_MAX)
            message = message.substring(0, MESSAGE_LENGTH_MAX);

        if (TextUtils.isEmpty(message)) return;
        cacheChatMessages.addMessage(userId, message);
    }
}
