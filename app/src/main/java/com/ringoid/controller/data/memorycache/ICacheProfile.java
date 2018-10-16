/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.net.Uri;

import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.model.ProfilePhoto;

import java.util.ArrayList;

public interface ICacheProfile {
    int getItemsNum();

    int getLikesNum(int position);

    String getImage(int pos);

    void setData(ArrayList<ProfilePhoto> photos);

    void addListener(ICacheProfileListener listener);

    String getImageId(int position);

    void removeItem(String imageId);

    boolean isDataExist();

    void addPhotoLocal(Uri fileUri, String clientPhotoId);

    boolean isPhotoLocal(int position);

    String getOriginPhotoId(int pos);

    int getPosition(String originPhotoId, int defaultValue);

    void resetCache();

    void updateLocalPhoto(String clientPhotoId, String originPhotoId);

    void removeItemByLocalPhotoId(String clientPhotoId);

    String getUrlThumbnail(int position);
}
