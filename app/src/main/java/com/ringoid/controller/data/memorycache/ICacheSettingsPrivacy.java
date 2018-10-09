/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;

public interface ICacheSettingsPrivacy {

    int getDistance();

    void setPrivacyDistance(int type);

    void addListener(ICacheSettingsPrivacyListener listener);

    boolean isCheckedPushMessages();

    boolean isCheckedPushMatches();

    String getPushLikes();

    int getPushLikesType();

    boolean isPushLikes();

    void setLikesType(int i);

    void setData(int safeDistanceInMeter, String pushLikes, boolean pushMessages, boolean pushMatches);

    boolean setCheckedPushMessages(boolean checked);

    boolean setCheckedPushMatches(boolean checked);

    boolean setPushLikes(boolean checked);

    void setPrivacyLikes(int i);
}
