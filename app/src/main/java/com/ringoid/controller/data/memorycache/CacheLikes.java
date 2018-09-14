/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.google.gson.Gson;
import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class CacheLikes implements ICacheLikes {

    private final String JSON_DATA = "{ \"data\":[" +
            "{\"id\":\"123\", \"urls\":[{\"isLiked\":true,\"id\":\"111\",\"url\":\"f4/01.jpg\"}, {\"id\":\"112\",\"url\":\"f4/02.jpg\"},{\"url\":\"f4/03.jpg\"}, {\"id\":\"113\",\"url\":\"f4/04.jpg\"}]}," +
            "{\"id\":\"1234\",\"urls\":[{\"id\":\"121\",\"url\":\"f5/01.jpg\"}, {\"id\":\"122\",\"url\":\"f5/02.jpg\"},{\"url\":\"f5/03.jpg\"}, {\"id\":\"123\",\"url\":\"f5/04.png\"},{\"id\":\"124\",\"url\":\"f5/05.jpg\"}]}," +
            "{\"id\":\"1235\",\"urls\":[{\"id\":\"131\",\"url\":\"f9/01.jpg\"}, {\"id\":\"132\",\"url\":\"f9/02.jpg\"},{\"url\":\"f9/03.jpg\"}, {\"id\":\"133\",\"url\":\"f9/04.jpg\"}]}" +
            "]  }";

    private ArrayList<DataProfile> data;
    private WeakHashMap<String, ICacheLikesListener> listeners;

    public CacheLikes() {
        data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
    }

    @Override
    public int getItemsNum() {
        return data.size();
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return data.get(adapterPosition).getItemsNum();
    }

    @Override
    public void setLiked(int adapterPosition, int itemPosition) {
        data.get(adapterPosition).setLiked(itemPosition);
        notifyListeners();
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).isLiked(itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).getImage(itemPosition);
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    @Override
    public void changeLiked(int adapterPosition, int itemPosition) {
        data.get(adapterPosition).changeLiked(itemPosition);
        notifyListeners();
    }

    @Override
    public String getItemId(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).getImageId(itemPosition);
    }

    @Override
    public String getUserId(int adapterPosition) {
        return data.get(adapterPosition).getId();
    }

    @Override
    public void addListener(ICacheLikesListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        return data.get(position).isLikedAnyPhoto();
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheLikesListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUpdate();
        }
    }

    private DataProfile getUserById(String userId) {
        if (data == null) return null;
        for (DataProfile item : data) {
            if (item.getId().equals(userId))
                return item;
        }
        return null;
    }

}
