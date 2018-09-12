/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;

public interface ICacheMessages {
    int getItemsNum();

    int getItemsNum(int adapterPosition);

    String getUrl(int adapterPosition, int itemPosition);

    void setUserSelected(int position);

    String getUrlSelectedUser();

    boolean isDataExist();

    boolean isLikedAnyPhoto(int position);

    boolean isMessagesExist(int position);

    String getUserId(int position);

    void setMessagesExist(String userId);

    void addListener(ICacheMessagesListener listener);
}
