package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICachePhotoRemove;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.repository.IRepositoryPhotoUploadSync;
import com.ringoid.controller.data.repository.IRepositoryProfileImageRemove;
import com.ringoid.model.PhotoRemove;
import com.ringoid.model.PhotoUpload;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;

public class HelperPhotoUpload implements IHelperPhotoUpload {

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICachePhotoUpload cachePhotoUpload;

    @Inject
    IRepositoryPhotoUploadSync repositoryPhotoUploadSync;

    @Inject
    IRepositoryProfileImageRemove repositoryPhotoRemove;

    @Inject
    ICacheUser cacheUser;

    @Inject
    ICachePhotoRemove cachePhotoRemove;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    public HelperPhotoUpload() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void addPhotoLocal(File file) {

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(file);
        photoUpload.setClientPhotoID(UUID.randomUUID().toString());
        cacheInterfaceState.setPhotoSelected(null, photoUpload.getClientPhotoId());
        cacheProfile.addPhotoLocal(photoUpload.getFileUri(), photoUpload.getClientPhotoId());
        cachePhotoUpload.add(photoUpload);

        proceedQueue();
    }

    private void proceedQueue() {
        repositoryPhotoUploadSync.request();
    }

    @Override
    public void remove(String photoId, String localId, String originId) {

        cacheProfile.removeItem(photoId, localId, originId);

        if (!cacheProfile.isDataExist())
            cacheUser.setUserNew();

        if (cachePhotoUpload.isContains(localId)) {

            cachePhotoUpload.removeItem(localId);

            if (!TextUtils.isEmpty(originId)) {
                repositoryPhotoRemove.request(null, originId);
                repositoryPhotoUploadSync.cancel(originId);
            }
            return;
        }

        if (!TextUtils.isEmpty(photoId)) {
            repositoryPhotoRemove.request(photoId, null);
            return;
        }

        if (!TextUtils.isEmpty(originId)) {
            repositoryPhotoRemove.request(null, originId);
            return;
        }
    }

    @Override
    public void checkState() {
        if (cachePhotoUpload.isDataExist())
            repositoryPhotoUploadSync.request();

        if (cachePhotoRemove.isDataExist()) {
            PhotoRemove item = cachePhotoRemove.getItemFirst();
            if (item == null) return;
            repositoryPhotoRemove.request(item.photoId, item.originId);
        }
    }

    @Override
    public int getPhotoUploadSize() {
        return cachePhotoUpload.getDataSize();
    }

    @Override
    public int getPhotoRemoveSize() {
        return cachePhotoRemove.getDataSize();
    }
}
