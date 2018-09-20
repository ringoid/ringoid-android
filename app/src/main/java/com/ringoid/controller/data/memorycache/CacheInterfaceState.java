package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelInterfaceState;

import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheInterfaceState implements ICacheInterfaceState {

    @Inject
    ICacheStorage cacheStorage;

    private ModelInterfaceState data;
    private WeakHashMap<String, ICacheInterfaceStateListener> listeners;

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

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.CACHE_INTERFACE);
    }

    private ModelInterfaceState getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_INTERFACE, ModelInterfaceState.class);
        if (data == null) {
            data = new ModelInterfaceState();
            resetCurrentPage();
        }
        return data;
    }

    @Override
    public void resetCurrentPage() {
        setCurrentPage(3);
    }

    @Override
    public void addListener(ICacheInterfaceStateListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getSimpleName(), listener);
    }

    @Override
    public void setCurrentPage(int i) {
        getData().setCurrentPage(i);
        saveData();
        notifyListenersPageUpdated();
    }

    @Override
    public int getCurrentPage() {
        return getData().getCurrentPage();
    }

    private void notifyListenersPageUpdated() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheInterfaceStateListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onPageSelected(getData().getCurrentPage());
        }
    }

}
