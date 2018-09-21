/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataProfile;
import com.ringoid.model.ModelFeedLikes;

import java.util.ArrayList;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheLikes implements ICacheLikes {

    @Inject
    ICacheStorage cacheStorage;

    private ModelFeedLikes data;
    private WeakHashMap<String, ICacheLikesListener> listeners;


    public CacheLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return getData().size();
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return getData().get(adapterPosition).getItemsNum();
    }

    @Override
    public void setLiked(int adapterPosition, int itemPosition) {
        getData().get(adapterPosition).setLiked(itemPosition);
        saveData();
        notifyListenersLiked(adapterPosition, itemPosition);
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return getData().get(adapterPosition).isLiked(itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return getData().get(adapterPosition).getImage(itemPosition);
    }

    @Override
    public boolean isDataExist() {
        return getData() != null && getData().size() > 0;
    }

    @Override
    public void changeLiked(int adapterPosition, int itemPosition) {
        getData().get(adapterPosition).changeLiked(itemPosition);
        if (getData().get(adapterPosition).isLiked(itemPosition))
            notifyListenersLiked(adapterPosition, itemPosition);
        else notifyListenersUnliked(adapterPosition, itemPosition);
        saveData();
    }

    @Override
    public String getItemId(int adapterPosition, int itemPosition) {
        return getData().get(adapterPosition).getImageId(itemPosition);
    }

    @Override
    public String getUserId(int adapterPosition) {
        return getData().get(adapterPosition).getId();
    }

    @Override
    public void addListener(ICacheLikesListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        return getData().get(position).isLikedAnyPhoto();
    }

    @Override
    public void setSelected(int adapterPosition, int firstVisibleItemPosition) {
        getData().get(adapterPosition).setSelected(firstVisibleItemPosition);
        saveData();
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return getData().get(position).getSelectedPosition();
    }

    private ModelFeedLikes getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_FEED_LIKES, ModelFeedLikes.class);
        if (data == null)
            data = new ModelFeedLikes();
        return data;
    }

    @Override
    public void setData(ArrayList<DataProfile> data) {
        this.data = new ModelFeedLikes(data);
        saveData();
        notifyListeners();
    }

    @Override
    public void resetCache() {
        this.data = null;
        cacheStorage.removeData(FileEnum.CACHE_FEED_LIKES);
        notifyListeners();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_FEED_LIKES, data);
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheLikesListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUpdate();
        }
    }

    private void notifyListenersLiked(int adapterPosition, int itemPosition) {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheLikesListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onLiked(adapterPosition, itemPosition);
        }
    }

    private void notifyListenersUnliked(int adapterPosition, int itemPosition) {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheLikesListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUnliked(adapterPosition, itemPosition);
        }
    }

}
