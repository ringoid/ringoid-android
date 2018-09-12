/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.net.Uri;

import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.model.ProfilePhoto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

public class CacheProfile implements ICacheProfile {


    private ArrayList<ProfilePhoto> data;
    private WeakHashMap<String, ICacheProfileListener> listeners;

    public CacheProfile() {

    }

    @Override
    public int getItemsNum() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getLikesNum(int position) {
        return data.get(position).getLikes();
    }

    @Override
    public String getImage(int pos) {
        return data.get(pos).getPhotoUri();
    }

    @Override
    public void setData(ArrayList<ProfilePhoto> photos) {
        if (data == null) data = new ArrayList<>();

        dedupblicate(data, photos);
        notifyListeners();
    }

    private void dedupblicate(ArrayList<ProfilePhoto> data, ArrayList<ProfilePhoto> photos) {
        Iterator<ProfilePhoto> iterator = data.iterator();
        while (iterator.hasNext()) {
            ProfilePhoto item = iterator.next();
            if (isContains(photos, item))
                iterator.remove();
        }
        data.addAll(photos);
    }

    private boolean isContains(ArrayList<ProfilePhoto> photos, ProfilePhoto other) {
        if (photos == null) return false;
        for (ProfilePhoto item : photos)
            if (item.getOriginPhotoId().equals(other.getOriginPhotoId()))
                return true;
        return false;
    }

    @Override
    public void addListener(ICacheProfileListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public String getImageId(int position) {
        return data == null ? null : data.get(position).getPhotoId();
    }

    @Override
    public void removeItem(String imageId) {
        if (data == null) return;
        ProfilePhoto item = getItem(imageId);
        if (!data.remove(item)) return;
        notifyListeners();
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    @Override
    public void addPhotoLocal(Uri fileUri, String photoId) {
        ProfilePhoto profilePhoto = new ProfilePhoto(fileUri, photoId);
        if (data == null) data = new ArrayList<>();
        data.add(profilePhoto);
        notifyListenersAddPhotoLocal(data.size() - 1);
    }

    @Override
    public void setPhotoLocalUploaded(String originPhotoId) {
        if (data == null) return;
        for (ProfilePhoto item : data) {
            if (item.getOriginPhotoId().equals(originPhotoId)) {
                item.setStatusUploaded();
                notifyListeners();
                return;
            }
        }
    }

    @Override
    public boolean isPhotoLocal(int position) {
        return data.get(position).isLocal();
    }

    @Override
    public boolean isPhotoUploading(int position) {
        return data.get(position).isUploading();
    }

    private ProfilePhoto getItem(String imageId) {
        if (data == null) return null;
        for (ProfilePhoto item : data)
            if (item.getPhotoId().equals(imageId))
                return item;
        return null;
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

}
