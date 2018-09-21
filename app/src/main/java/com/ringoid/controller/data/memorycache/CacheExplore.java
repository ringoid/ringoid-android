/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheExploreListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataProfile;
import com.ringoid.model.ModelFeedExplore;

import java.util.ArrayList;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheExplore implements ICacheExplore {

    @Inject
    ICacheStorage cacheStorage;

    private ModelFeedExplore data;
    private WeakHashMap<String, ICacheExploreListener> listeners;

    public CacheExplore() {
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
    public void changeLiked(int adapterPosition, int itemPosition) {
        getData().get(adapterPosition).changeLiked(itemPosition);
        saveData();
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

    private ModelFeedExplore getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_FEED_EXPLORE, ModelFeedExplore.class);
        if (data == null) data = new ModelFeedExplore();
        return data;
    }

    @Override
    public void setData(ArrayList<DataProfile> data) {
        this.data = new ModelFeedExplore(data);
        saveData();
        notifyListeners();
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheExploreListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUpdate();
        }
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_FEED_EXPLORE, data);
    }

    @Override
    public void resetCache() {
        this.data = null;
        cacheStorage.removeData(FileEnum.CACHE_FEED_EXPLORE);
    }

    @Override
    public void addListener(ICacheExploreListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public boolean isDataExist() {
        return getData().size() > 0;
    }

}
