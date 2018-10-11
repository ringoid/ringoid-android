/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.net.Uri;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelProfilePhotos;
import com.ringoid.model.ProfilePhoto;

import java.util.ArrayList;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheProfile implements ICacheProfile {

    @Inject
    ICacheStorage cacheStorage;

    private ModelProfilePhotos data;
    private WeakHashMap<String, ICacheProfileListener> listeners;

    public CacheProfile() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return getData() == null ? 0 : data.size();
    }

    private ModelProfilePhotos getData() {
        if (data == null) {
            data = cacheStorage.readObject(FileEnum.CACHE_PROFILE, ModelProfilePhotos.class);
            if (data != null && data.removeLocalPhotos())
                saveData();
        }
        if (data == null) data = new ModelProfilePhotos();
        return data;
    }

    @Override
    public void setData(ArrayList<ProfilePhoto> photos) {
        getData().add(photos);
        saveData();
        notifyListeners();
    }

    @Override
    public int getLikesNum(int position) {
        return getData().getLikes(position);
    }

    @Override
    public String getImage(int pos) {
        return getData().getPhotoUri(pos);
    }

    @Override
    public void addListener(ICacheProfileListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public String getImageId(int position) {
        return getData().getPhotoId(position);
    }

    @Override
    public void removeItem(String imageId) {
        int index = getItemIndex(imageId, -1);
        if (index == -1) return;

        if (!getData().remove(index)) return;

        saveData();
        notifyListenersRemove(index);
    }

    private int getItemIndex(String imageId, int defaultValue) {
        for (int i = 0; i < getData().size(); ++i)
            if (getData().isEquals(i, imageId))
                return i;
        return -1;
    }

    @Override
    public boolean isDataExist() {
        return getData().size() > 0;
    }

    @Override
    public void addPhotoLocal(Uri fileUri, String clientPhotoId) {
        ProfilePhoto profilePhoto = new ProfilePhoto(fileUri, clientPhotoId);
        getData().add(profilePhoto);
        saveData();
        notifyListenersAddPhotoLocal(getData().size() - 1);
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_PROFILE, data);
    }

    @Override
    public boolean isPhotoLocal(int position) {
        return getData().isLocal(position);
    }

    @Override
    public String getOriginPhotoId(int pos) {
        return getData().getOriginPhotoId(pos);
    }

    @Override
    public int getPosition(String originPhotoId, int defaultValue) {
        return getData().getPositionByOriginPhotoId(originPhotoId, defaultValue);
    }

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.CACHE_PROFILE);
        notifyListeners();
    }

    @Override
    public void updateLocalPhoto(String clientPhotoId, String originPhotoId) {
        ProfilePhoto item = getData().getItemByClientPhotoId(clientPhotoId);
        if (item == null) return;
        item.setOriginPhotoId(originPhotoId);
        saveData();
        notifyListeners();
    }

    @Override
    public void removeItemByLocalPhotoId(String clientPhotoId) {
        ProfilePhoto item = getData().getItemByClientPhotoId(clientPhotoId);
        if (item == null) return;
        getData().remove(item);
        saveData();
        notifyListeners();
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheProfileListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUpdate();
        }
    }

    private void notifyListenersAddPhotoLocal(int position) {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheProfileListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onPhotoAdd(position);
        }
    }


    private void notifyListenersRemove(int index) {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheProfileListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onPhotoRemove(index);
        }
    }

}
