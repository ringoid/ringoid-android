package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.model.PhotoUpload;

public interface ICachePhotoUpload {
    void add(PhotoUpload item);

    void resetCache();

    boolean isDataExist();

    PhotoUpload getItemFirst();

    void removeItem(String photoOriginId);

    boolean isContains(String localId);

    int getDataSize();
}
