package com.ringoid.controller.data.network.response;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.model.ProfilePhoto;

import java.util.ArrayList;

public class ResponseProfilePhotos extends ResponseBase {
    private ArrayList<ProfilePhoto> photos;

    public ArrayList<ProfilePhoto> getPhotos() {
        return photos;
    }
}
