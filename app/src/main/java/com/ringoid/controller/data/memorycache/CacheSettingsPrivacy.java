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
}
