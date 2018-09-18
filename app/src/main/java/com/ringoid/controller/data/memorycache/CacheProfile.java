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
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_PROFILE, ModelProfilePhotos.class);
        return data;
    }

    @Override
    public void setData(ArrayList<ProfilePhoto> photos) {
        if (getData() == null)
            data = new ModelProfilePhotos();
        data.add(photos);
        cacheStorage.writeData(FileEnum.CACHE_PROFILE, data);
        notifyListeners();
    }

    @Override
    public int getLikesNum(int position) {
        if (getData() == null) return 0;
        return data.getLikes(position);
    }

    @Override
    public String getImage(int pos) {
        if (getData() == null) return null;
        return data.getPhotoUri(pos);
    }

    @Override
    public void addListener(ICacheProfileListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public String getImageId(int position) {
        return getData() == null ? null : data.getPhotoId(position);
    }

    @Override
    public void removeItem(String imageId) {
        int index = getItemIndex(imageId, -1);
        if (index == -1) return;

        if (!data.remove(index)) return;

        cacheStorage.writeData(FileEnum.CACHE_PROFILE, data);
        notifyListenersRemove(index);
    }

    private int getItemIndex(String imageId, int defaultValue) {
        if (getData() == null) return -1;

        for (int i = 0; i < data.size(); ++i)
            if (data.isEquals(i, imageId))
                return i;
        return -1;
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    @Override
    public void addPhotoLocal(Uri fileUri, String photoId) {
        ProfilePhoto profilePhoto = new ProfilePhoto(fileUri, photoId);
        if (getData() == null) data = new ModelProfilePhotos();
        data.add(profilePhoto);
        cacheStorage.writeData(FileEnum.CACHE_PROFILE, data);
        notifyListenersAddPhotoLocal(data.size() - 1);
    }

    @Override
    public void setPhotoLocalUploaded(String originPhotoId) {
        if (getData() == null) return;
        if (data.setPhotoLocalUploaded(originPhotoId)) {
            cacheStorage.writeData(FileEnum.CACHE_PROFILE, data);
            notifyListeners();
        }
    }

    @Override
    public boolean isPhotoLocal(int position) {
        return getData() == null ? false : data.isLocal(position);
    }

    @Override
    public boolean isPhotoUploading(int position) {
        return getData() == null ? false : data.isUploading(position);
    }

    @Override
    public String getOriginPhotoId(int pos) {
        return getData() == null ? null : data.getOriginPhotoId(pos);
    }

    @Override
    public int getPosition(String originPhotoId) {
        return getData() == null ? 0 : data.getPosition(originPhotoId);
    }

    private ProfilePhoto getItem(String imageId) {
        if (getData() == null) return null;
        return data.getItem(imageId);
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
