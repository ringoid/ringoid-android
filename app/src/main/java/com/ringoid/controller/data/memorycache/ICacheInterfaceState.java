package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface ICacheInterfaceState {
    void setProfileOriginPhotoId(String originPhotoId);

    String getOriginPhotoId();

    void resetCache();
}
