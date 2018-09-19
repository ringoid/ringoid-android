package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestPhotoUploadUri {
    private String accessToken;
    private String extension;
    private String clientPhotoId;

    public RequestPhotoUploadUri(String accessToken, String clientPhotoId, String extension) {
        this.accessToken = accessToken;
        this.extension = extension;
        this.clientPhotoId = clientPhotoId;
    }
}
