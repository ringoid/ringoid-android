package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelInterfaceState;

import javax.inject.Inject;

public class CacheInterfaceState implements ICacheInterfaceState {

    @Inject
    ICacheStorage cacheStorage;

    private ModelInterfaceState data;


    public CacheInterfaceState() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setProfileOriginPhotoId(String originPhotoId) {
        getData().setOriginPhotoId(originPhotoId);
        saveData();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_INTERFACE, data);
    }

    @Override
    public String getOriginPhotoId() {
        return getData().getOriginPhotoId();
    }

    private ModelInterfaceState getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_INTERFACE, ModelInterfaceState.class);
        if (data == null)
            data = new ModelInterfaceState();
        return data;
    }
}
