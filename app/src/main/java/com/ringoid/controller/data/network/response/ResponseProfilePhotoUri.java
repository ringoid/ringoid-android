package com.ringoid.controller.data.network.response;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class ResponseProfilePhotoUri extends ResponseBase {
    private String uri;
    private String originPhotoId;
    private String clientPhotoId;

    public String getUri() {
        return uri;
    }

    public String getOriginPhotoId() {
        return originPhotoId;
    }

    public String getClientPhotoId() {
        return clientPhotoId;
    }
}
