package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamSettingsUpdate {

    private String accessToken;
    private String whoCanSeePhoto; // possible values PHOTOS_OPPOSITE/INCOGNITO/ONLY_ME
    private int safeDistanceInMeter;
    private boolean pushMessages;
    private boolean pushMatches;
    private String pushLikes; //possible values NONE/EVERY/10_NEW/100_NEW

    public RequestParamSettingsUpdate(
            String accessToken,
            String whoCanSeePhoto,
            int safeDistanceInMeter,
            boolean pushMessages,
            boolean pushMatches,
            String pushLikes) {

        this.accessToken = accessToken;
        this.whoCanSeePhoto = whoCanSeePhoto;
        this.safeDistanceInMeter = safeDistanceInMeter;
        this.pushMessages = pushMessages;
        this.pushMatches = pushMatches;
        this.pushLikes = pushLikes;
    }
}
