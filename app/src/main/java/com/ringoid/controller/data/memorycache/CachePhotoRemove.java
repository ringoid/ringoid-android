package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelPhotoRemove;
import com.ringoid.model.PhotoRemove;

import javax.inject.Inject;

public class CachePhotoRemove implements ICachePhotoRemove {

    @Inject
    ICacheStorage cacheStorage;

    private ModelPhotoRemove data;

    public CachePhotoRemove() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void add(String photoId, String originId) {
        PhotoRemove item = new PhotoRemove();
        item.photoId = photoId;
        item.originId = originId;

        getData().add(item);
        saveData();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.PHOTO_REMOVE, data);
    }

    private ModelPhotoRemove getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.PHOTO_REMOVE, ModelPhotoRemove.class);
        if (data == null)
            data = new ModelPhotoRemove();
        return data;
    }

    @Override
    public boolean isDataExist() {
        return getData().size() > 0;
    }

    @Override
    public PhotoRemove getItemFirst() {
        return getData().get(0);
    }

    @Override
    public void remove(String photoId, String originId) {
        if (!getData().remove(photoId, originId))
            return;
        saveData();
    }

    @Override
    public void resetCache() {
        cacheStorage.removeData(FileEnum.PHOTO_REMOVE);
    }

    @Override
    public boolean isContains(String photoId) {
        return getData().isContains(photoId);
    }

    @Override
    public int getDataSize() {
        return getData().size();
    }
}
