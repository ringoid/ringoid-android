/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public class CacheSettingsPrivacy implements ICacheSettingsPrivacy {

    private int privacyPhotosType;
    private int distanceType;

    @Override
    public int getPrivacyPhotos() {
        return privacyPhotosType;
    }

    @Override
    public void setPrivacyPhotos(int i) {
        this.privacyPhotosType = i;
    }

    @Override
    public int getDistanceType() {
        return distanceType;
    }

    @Override
    public void setPrivacyDistance(int type) {
        this.distanceType = type;
    }

    @Override
    public boolean isPrivacyPhotosOppositeSex() {
        return privacyPhotosType == 0;
    }

    @Override
    public boolean isPrivacyPhotosLikes() {
        return privacyPhotosType == 1;
    }

    @Override
    public boolean isPrivacyPhotosNoone() {
        return privacyPhotosType == 2;
    }
}
