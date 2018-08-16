/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;

public interface ICacheChatMessages {
    void resetCache();

    void addMessage(String message);

    boolean isDataExist();

    boolean isSendEnabled();

    void addListener(ICacheChatMessagesListener listener);

    int getDataSize();

    boolean isSelf(int position);

    String getUrl(int position);

    String getMessage(int position);
}
