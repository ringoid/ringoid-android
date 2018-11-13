package com.ringoid.controller.data.memorycache;

import com.ringoid.model.PhotoRemove;

public interface ICachePhotoRemove {
    void add(String photoId, String originId);

    boolean isDataExist();

    PhotoRemove getItemFirst();

    void remove(String photoId, String originId);

    void resetCache();

    boolean isContains(String photoId);

    int getDataSize();

}
