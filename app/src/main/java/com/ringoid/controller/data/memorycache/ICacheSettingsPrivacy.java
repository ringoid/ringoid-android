/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheSettingsPrivacy {
    void setPrivacyPhotos(int i);

    int getPrivacyPhotos();

    int getDistanceType();

    void setPrivacyDistance(int type);

    boolean isPrivacyPhotosOppositeSex();

    boolean isPrivacyPhotosLikes();

    boolean isPrivacyPhotosNoone();
}
