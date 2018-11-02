package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelPhotoUpload;
import com.ringoid.model.PhotoUpload;

import javax.inject.Inject;

public class CachePhotoUpload implements ICachePhotoUpload {

    @Inject
    ICacheStorage cacheStorage;

    private ModelPhotoUpload data;

    public CachePhotoUpload() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void add(PhotoUpload item) {
        getData().add(item);
        saveData();
    }

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.CACHE_PHOTO_UPLOAD);
    }

    @Override
    public synchronized boolean isDataExist() {
        return getData().size() > 0;
    }

    @Override
    public synchronized PhotoUpload getItemFirst() {
        return getData().getItem(0);
    }

    @Override
    public void removeItem(String photoClientId) {
        if (!getData().remove(photoClientId))
            return;
        saveData();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_PHOTO_UPLOAD, data);
    }

    private ModelPhotoUpload getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_PHOTO_UPLOAD, ModelPhotoUpload.class);
        if (data == null) data = new ModelPhotoUpload();
        return data;
    }
}
