package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;

import java.io.File;
import java.io.Serializable;

public class PhotoUpload implements Serializable {

    private File file;
    private String clientPhotoId;

    public Uri getFileUri() {
        return Uri.parse("file://" + file.getPath());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public synchronized String getClientPhotoId() {
        return clientPhotoId;
    }

    public void setClientPhotoID(String s) {
        this.clientPhotoId = s;
    }
}
