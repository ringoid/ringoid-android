package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;

import java.io.File;

public interface ICachePhotoUpload {
    void setUri(Uri file);

    void setUploadUri(String uri);

    boolean isDataExist();

    Uri getFileUri();

    String getUploadUri();

    File getFile();

    void setFile(File file);

    void setOriginPhotoId(String photoId);

    String getPhotoId();
}
