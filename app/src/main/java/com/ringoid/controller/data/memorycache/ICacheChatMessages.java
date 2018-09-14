/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;

public interface ICacheChatMessages {
    void resetCache();

    void addMessage(String userSelectedID, String message);

    boolean isDataExist(String userSelectedID);

    void addListener(ICacheChatMessagesListener listener);

    int getDataSize(String urlSelectedUser);

    boolean isSelf(String userSelectedID, int position);

    String getMessage(String userSelectedID, int position);

    void resetCache(String userSelectedID);

    boolean isMessageNew(String userId);

    void setReaded(String userSelectedID);
}
