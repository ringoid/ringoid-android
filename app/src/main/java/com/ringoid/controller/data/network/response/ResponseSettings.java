package com.ringoid.controller.data.network.response;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class ResponseSettings extends ResponseBase{

    private String whoCanSeePhoto; // possible values PHOTOS_OPPOSITE/INCOGNITO/ONLY_ME
    private int safeDistanceInMeter;
    private boolean pushMessages;
    private boolean pushMatches;
    private String pushLikes; //possible values NONE/EVERY/10_NEW/100_NEW

    public String getWhoCanSeePhoto() {
        return whoCanSeePhoto;
    }

    public int getSafeDistanceInMeter() {
        return safeDistanceInMeter;
    }

    public boolean isPushMessages() {
        return pushMessages;
    }

    public boolean isPushMatches() {
        return pushMatches;
    }

    public String getPushLikes() {
        return pushLikes;
    }
}
