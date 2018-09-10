package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamProfileImageRemove {
    private String accessToken;
    private String photoId;

    public RequestParamProfileImageRemove(String token, String imageId) {
        this.accessToken = token;
        this.photoId = imageId;
    }
}
