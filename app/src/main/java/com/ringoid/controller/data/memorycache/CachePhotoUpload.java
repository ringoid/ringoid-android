package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

public class CachePhotoUpload implements ICachePhotoUpload {
    private Uri uri;
    private String uploadUri;
    private File file;
    private String originPhotoId;
    private String clientPhotoId;

    @Override
    public void setUri(Uri file) {
        this.uri = file;
    }

    @Override
    public boolean isDataExist() {
        return !TextUtils.isEmpty(uploadUri) && uri != null;
    }

    @Override
    public Uri getFileUri() {
        return uri;
    }

    @Override
    public String getUploadUri() {
        return uploadUri;
    }

    @Override
    public void setUploadUri(String uri) {
        this.uploadUri = uri;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void setOriginPhotoId(String clientPhotoId, String photoId) {
        if (!clientPhotoId.equals(this.clientPhotoId)) return;
        this.originPhotoId = photoId;
    }

    @Override
    public String getOriginPhotoId() {
        return originPhotoId;
    }

    @Override
    public String getClientPhotoId() {
        return clientPhotoId;
    }

    @Override
    public void setClientPhotoID(String s) {
        this.clientPhotoId = s;
    }
}
