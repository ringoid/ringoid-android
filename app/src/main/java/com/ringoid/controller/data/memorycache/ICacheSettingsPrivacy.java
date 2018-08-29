/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;

public interface ICacheSettingsPrivacy {
    void setPrivacyPhotos(int i);

    int getPrivacyPhotos();

    int getDistanceType();

    void setPrivacyDistance(int type);

    boolean isPrivacyPhotosOppositeSex();

    boolean isPrivacyPhotosLikes();

    boolean isPrivacyPhotosNoone();

    void addListener(ICacheSettingsPrivacyListener listener);

    void changeCheckedPushMessages();

    void changeCheckedPushMatches();

    boolean isCheckedPushMessages();

    boolean isCheckedPushMatches();

    int getDistanceSafeMeter();

    String getWhoCanSeePhotosString();

    String getPushLikes();

    int getPushLikesType();

    boolean isPushLikes();

    void changePushLikes();

    void setLikesType(int i);

    void setData(String whoCanSeePhoto, int safeDistanceInMeter, String pushLikes, boolean pushMessages, boolean pushMatches);
}
