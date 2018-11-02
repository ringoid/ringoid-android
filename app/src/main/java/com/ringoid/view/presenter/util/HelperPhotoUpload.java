package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.repository.IRepositoryPhotoUploadSync;
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

    public HelperPhotoUpload() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void addPhotoLocal(File file) {

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(file);
        photoUpload.setClientPhotoID(UUID.randomUUID().toString());

        cacheProfile.addPhotoLocal(photoUpload.getFileUri(), photoUpload.getClientPhotoId());
        cachePhotoUpload.add(photoUpload);

        proceedQueue();
    }

    private void proceedQueue() {
        repositoryPhotoUploadSync.request();
    }
}
