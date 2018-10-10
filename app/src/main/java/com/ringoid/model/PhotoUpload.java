package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

public class PhotoUpload {

    private String uploadUri;
    private File file;
    private String originPhotoId;
    private String clientPhotoId;

    public boolean isDataExist() {
        return !TextUtils.isEmpty(uploadUri) && file != null;
    }

    public Uri getFileUri() {
        return Uri.parse("file://" + file.getPath());
    }

    public String getUploadUri() {
        return uploadUri;
    }

    public void setUploadUri(String uri) {
        this.uploadUri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setOriginPhotoId(String clientPhotoId, String photoId) {
        if (!clientPhotoId.equals(this.clientPhotoId)) return;
        this.originPhotoId = photoId;
    }

    public String getOriginPhotoId() {
        return originPhotoId;
    }

    public String getClientPhotoId() {
        return clientPhotoId;
    }

    public void setClientPhotoID(String s) {
        this.clientPhotoId = s;
    }
}
