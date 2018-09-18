package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class ModelInterfaceState implements Serializable{
    private String originPhotoId;

    public void setOriginPhotoId(String originPhotoId) {
        this.originPhotoId = originPhotoId;
    }

    public String getOriginPhotoId() {
        return originPhotoId;
    }
}
