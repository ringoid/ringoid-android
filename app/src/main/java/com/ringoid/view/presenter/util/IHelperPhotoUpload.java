package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.File;

public interface IHelperPhotoUpload {
    void addPhotoLocal(File file);

    void remove(String imageId, String localId, String originId);

    void checkState();

    int getPhotoUploadSize();

    int getPhotoRemoveSize();
}
