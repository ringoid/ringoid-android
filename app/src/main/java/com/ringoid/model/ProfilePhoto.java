package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;

import java.io.Serializable;

public class ProfilePhoto implements Serializable {
    private static final int STATUS_UPLOADED = 2;
    private static final int STATUS_UPLOADING = 1;
    private String clientPhotoId;
    private String photoId;
    private String originPhotoId;
    private String photoUri;
    private int likes;
    private boolean isLocal;
    private int status;

    //constructor for Gson
    public ProfilePhoto() {
        isLocal = false;
    }

    public ProfilePhoto(Uri fileUri, String clientPhotoId) {
        isLocal = true;
        this.photoUri = fileUri.toString();
        this.clientPhotoId = clientPhotoId;
        status = STATUS_UPLOADING;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public int getLikes() {
        return likes;
    }

    public String getOriginPhotoId() {
        return originPhotoId;
    }

    public void setOriginPhotoId(String originPhotoId) {
        this.originPhotoId = originPhotoId;
    }

    public void setStatusUploaded() {
        status = STATUS_UPLOADED;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public boolean isUploading() {
        return status == STATUS_UPLOADING;
    }

    public boolean isEqualsClientPhotoId(String clientPhotoId) {
        return this.clientPhotoId != null && this.clientPhotoId.equals(clientPhotoId);
    }

    public boolean isEqualsOriginPhotoId(String originPhotoId) {
        return this.originPhotoId != null && this.originPhotoId.equals(originPhotoId);
    }
}
