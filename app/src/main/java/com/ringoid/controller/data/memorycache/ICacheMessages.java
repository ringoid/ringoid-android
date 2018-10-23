/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

public interface ICacheMessages {
    int getItemsNum();

    int getItemsNum(int adapterPosition);

    String getUrl(int adapterPosition, int itemPosition);

    String getUrlSelectedUser();

    boolean isDataExist();

    boolean isLikedAnyPhoto(int position);

    String getUserId(int position);

    void addListener(ICacheMessagesListener listener);

    String getUserSelectedID();

    void setUserSelected(DataProfile user);

    void setSelected(int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);

    void setData(ArrayList<DataProfile> data);

    void resetCache();

    int getPosition(String userSelectedID, int noValue);

    DataProfile getUser(int position);
}
