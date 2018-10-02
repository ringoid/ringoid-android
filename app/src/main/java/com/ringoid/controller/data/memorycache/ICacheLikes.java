/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

public interface ICacheLikes {

    int getItemsNum();

    int getItemsNum(int adapterPosition);

    void setLiked(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);

    boolean isDataExist();

    void changeLiked(int adapterPosition, int itemPosition);

    String getUserId(int adapterPosition);

    void addListener(ICacheLikesListener listener);

    boolean isLikedAnyPhoto(int position);

    void setSelected(int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);

    void setData(ArrayList<DataProfile> data);

    void resetCache();

    int getPosition(String userId, int noValue);
}
