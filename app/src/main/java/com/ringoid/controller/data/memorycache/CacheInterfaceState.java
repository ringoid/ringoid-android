package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelInterfaceState;
import com.ringoid.view.presenter.PresenterPagesContainer;

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
        setCurrentPage(PresenterPagesContainer.INDEX_PAGE_EXPLORE);
    }

    @Override
    public void addListener(ICacheInterfaceStateListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public int getCurrentPage() {
        return getData().getCurrentPage();
    }

    @Override
    public void setCurrentPage(int i) {
        getData().setCurrentPage(i);
        saveData();
        notifyListenersPageUpdated();
    }

    @Override
    public int getPositionScrollPageLikes() {
        return getData().getPositionScrollPageLikes();
    }

    @Override
    public void setPositionScrollPageLikes(int position, int offset) {
        getData().setPositionScrollPageLikes(position, offset);
        saveData();
    }

    @Override
    public int getPositionScrollPageMessages() {
        return getData().getPositionScrollPageMessages();
    }

    @Override
    public void setPositionScrollPageMessages(int position, int offset) {
        getData().setPositionScrollPageMessages(position, offset);
        saveData();
    }

    @Override
    public void setPositionScrollPageExplore(int firstVisibleItemPosition, int offset) {
        getData().setPositionScrollPageExplore(firstVisibleItemPosition, offset);
        saveData();
    }

    @Override
    public int getPositionScrollPageExploreOffset() {
        return getData().getPositionScrollPageExploreOffset();
    }

    @Override
    public int getPositionScrollPageLikesOffset() {
        return getData().getPositionScrollPageLikesOffset();
    }

    @Override
    public int getPositionScrollPageMessagesOffset() {
        return getData().getPositionScrollPageMessagesOffset();
    }

    @Override
    public void resetCachePositionLikes() {
        setPositionScrollPageLikes(0, 0);
    }

    @Override
    public void resetCachePositionMessage() {
        setPositionScrollPageMessages(0, 0);
    }

    @Override
    public void resetCachePositionExplore() {
        setPositionScrollPageExplore(0, 0);
    }

    @Override
    public int getPositionScrollPageExplore() {
        return getData().getPositionScrollPageExplore();
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
