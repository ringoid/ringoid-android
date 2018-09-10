/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheProfileListener;
import com.ringoid.model.ProfilePhoto;

import java.util.ArrayList;
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
        this.data = photos;
        notifyListeners();
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
}
