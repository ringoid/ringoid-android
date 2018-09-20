package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;

public interface ICacheInterfaceState {
    void setProfileOriginPhotoId(String originPhotoId);

    String getOriginPhotoId();

    void resetCache();

    void resetCurrentPage();

    void addListener(ICacheInterfaceStateListener listener);

    void setCurrentPage(int i);

    int getCurrentPage();
}
