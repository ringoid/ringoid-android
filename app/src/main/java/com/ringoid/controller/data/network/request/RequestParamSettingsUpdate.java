package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamSettingsUpdate {

    private String accessToken;
    private int safeDistanceInMeter;
    private boolean pushMessages;
    private boolean pushMatches;
    private String pushLikes; //possible values NONE/EVERY/10_NEW/100_NEW

    public RequestParamSettingsUpdate(
            String accessToken,
            int safeDistanceInMeter,
            boolean pushMessages,
            boolean pushMatches,
            String pushLikes) {

        this.accessToken = accessToken;
        this.safeDistanceInMeter = safeDistanceInMeter;
        this.pushMessages = pushMessages;
        this.pushMatches = pushMatches;
        this.pushLikes = pushLikes;
    }
}
