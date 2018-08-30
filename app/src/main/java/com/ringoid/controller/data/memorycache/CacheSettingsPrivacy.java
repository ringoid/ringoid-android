/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheSettingsPrivacyListener;

import java.util.WeakHashMap;

public class CacheSettingsPrivacy implements ICacheSettingsPrivacy {

    private static final String LIKES_NONE = "NONE";
    private static final String LIKES_EVERY = "EVERY";
    private static final String LIKES_10 = "10_NEW";
    private static final String LIKES_100 = "100_NEW";
    private static final String PHOTOS_OPPOSITE = "OPPOSITE";
    private static final String PHOTOS_INCOGNITO = "INCOGNITO";
    private static final String PHOTOS_ONLY_ME = "ONLY_ME";

    private int privacyPhotosType;
    private int distance;
    private int pushLikesType;
    private boolean isPushEnabledMessages;
    private boolean isPushEnabledMatches;

    private WeakHashMap<String, ICacheSettingsPrivacyListener> listeners;

    @Override
    public int getPrivacyPhotos() {
        return privacyPhotosType;
    }

    @Override
    public void setPrivacyPhotos(int i) {
        this.privacyPhotosType = i;
        notifyListeners();
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheSettingsPrivacyListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUpdate();
        }
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public void setPrivacyDistance(int distance) {
        this.distance = distance;
        notifyListeners();
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

    @Override
    public void addListener(ICacheSettingsPrivacyListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public boolean isCheckedPushMessages() {
        return isPushEnabledMessages;
    }

    @Override
    public boolean isCheckedPushMatches() {
        return isPushEnabledMatches;
    }

    @Override
    public String getWhoCanSeePhotosString() {
        return isPrivacyPhotosOppositeSex()
                ? PHOTOS_OPPOSITE
                : isPrivacyPhotosLikes()
                ? PHOTOS_INCOGNITO
                : PHOTOS_ONLY_ME;
    }

    @Override
    public String getPushLikes() {
        return pushLikesType == 0
                ? LIKES_NONE
                : pushLikesType == 1
                ? LIKES_EVERY
                : pushLikesType == 2
                ? LIKES_10
                : pushLikesType == 3
                ? LIKES_100
                : null;
    }

    @Override
    public int getPushLikesType() {
        return pushLikesType;
    }

    @Override
    public boolean isPushLikes() {
        return pushLikesType != 0;
    }

    @Override
    public void setLikesType(int i) {
        pushLikesType = i;
        notifyListeners();
    }

    @Override
    public void setData(String whoCanSeePhoto, int safeDistanceInMeter, String pushLikes, boolean pushMessages, boolean pushMatches) {
        this.privacyPhotosType = whoCanSeePhoto.equals(PHOTOS_OPPOSITE)
                ? 0
                : whoCanSeePhoto.equals(PHOTOS_INCOGNITO)
                ? 1
                : whoCanSeePhoto.equals(PHOTOS_ONLY_ME)
                ? 2
                : 2;

        this.distance = safeDistanceInMeter;

        this.isPushEnabledMatches = pushMatches;
        this.isPushEnabledMessages = pushMessages;

        this.pushLikesType = pushLikes.equals(LIKES_NONE)
                ? 0
                : pushLikes.equals(LIKES_EVERY)
                ? 1
                : pushLikes.equals(LIKES_10)
                ? 2
                : pushLikes.equals(LIKES_100)
                ? 3
                : 0;

        notifyListeners();
    }

    @Override
    public boolean setCheckedPushMessages(boolean checked) {
        if (isPushEnabledMessages == checked) return false;

        isPushEnabledMessages = checked;
        notifyListeners();
        return true;
    }

    @Override
    public boolean setCheckedPushMatches(boolean checked) {
        if (isPushEnabledMatches == checked) return false;

        isPushEnabledMatches = checked;
        notifyListeners();
        return true;
    }

    @Override
    public boolean setPushLikes(boolean checked) {
        if (pushLikesType == 0 && !checked
                || pushLikesType != 0 && checked) return false;

        pushLikesType = checked ? 1 : 0;
        notifyListeners();
        return true;
    }
}
