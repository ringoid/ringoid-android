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

    @Inject
    ICachePhotoRemove cachePhotoRemove;

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

        if (photos != null && photos.size() > 0) {
            for (ProfilePhoto item : photos) {
                if (cachePhotoRemove.isContains(item.getPhotoId()))
                    continue;
                getData().add(item);
            }
            saveData();
        }

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
    public void removeItem(String imageId, String localId, String originId) {
        int index = getItemIndex(imageId, localId, originId, -1);
        if (index == -1) return;

        if (!getData().remove(index)) return;

        saveData();
        notifyListenersRemove(index);
    }

    private int getItemIndex(String imageId, String localId, String originId, int defaultValue) {
        for (int i = 0; i < getData().size(); ++i)
            if (getData().isEquals(i, imageId, localId, originId))
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
        notifyListenersAddPhotoLocal(0);
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_PROFILE, data);
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
    public String getUrlThumbnail(int position) {
        return getData().getUrlThumbnail(position);
    }

    @Override
    public String getPhotoLocalId(int position) {
        return getData().getPhotoLocalId(position);
    }

    @Override
    public String getPhotoOriginId(int position) {
        return getData().getPhotoOriginId(position);
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
