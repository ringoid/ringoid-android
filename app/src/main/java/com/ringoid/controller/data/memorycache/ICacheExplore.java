/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheExploreListener;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

public interface ICacheExplore {
    int getItemsNum();

    int getItemsNum(int adapterPosition);

    void setLiked(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);

    void changeLiked(int adapterPosition, int itemPosition);
    
    void setSelected(int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);

    boolean isDataExist();

    void setData(ArrayList<DataProfile> data);

    void resetCache();

    void addListener(ICacheExploreListener iCacheExploreListener);
}
