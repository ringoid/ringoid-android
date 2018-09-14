/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;

public interface ICacheSettingsPrivacy {
    void setPrivacyPhotos(int i);

    int getPrivacyPhotos();

    int getDistance();

    void setPrivacyDistance(int type);

    boolean isPrivacyPhotosOppositeSex();

    boolean isPrivacyPhotosLikes();

    boolean isPrivacyPhotosNoone();

    void addListener(ICacheSettingsPrivacyListener listener);

    boolean isCheckedPushMessages();

    boolean isCheckedPushMatches();

    String getWhoCanSeePhotosString();

    String getPushLikes();

    int getPushLikesType();

    boolean isPushLikes();

    void setLikesType(int i);

    void setData(String whoCanSeePhoto, int safeDistanceInMeter, String pushLikes, boolean pushMessages, boolean pushMatches);

    boolean setCheckedPushMessages(boolean checked);

    boolean setCheckedPushMatches(boolean checked);

    boolean setPushLikes(boolean checked);

    int getPrivacyAgeMin();

    int getPrivacyAgeMax();

    void setAgeSelected(int min, int max);
}
