/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.google.gson.Gson;
import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class CacheMessages implements ICacheMessages {

    private final String JSON_DATA = "{ \"data\":[" +
            "{\"messagesExist\":true,\"id\":123,\"urls\":[{\"isLiked\":true,\"url\":\"f6/01.jpg\"},{\"url\":\"f6/02.jpg\"},{\"url\":\"f6/03.jpg\"},{\"url\":\"f6/04.jpg\"},{\"url\":\"f6/05.jpg\"}]}," +
            "{\"id\":174,\"urls\":[{\"url\":\"f8/01.jpg\"},{\"isLiked\":true,\"url\":\"f8/02.jpg\"},{\"url\":\"f8/03.jpg\"}]}," +
            "{\"id\":5253,\"urls\":[{\"url\":\"f9/01.jpg\"},{\"url\":\"f9/02.jpg\"},{\"url\":\"f9/03.jpg\"},{\"isLiked\":true,\"url\":\"f9/04.jpg\"}]}" +
            "]  }";

    private ArrayList<DataProfile> data;
    private String selectedUserId;
    private WeakHashMap<String, ICacheMessagesListener> listeners;

    public CacheMessages() {
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
    public String getUrl(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).getImage(itemPosition);
    }

    @Override
    public void setUserSelected(int position) {
        this.selectedUserId = data.get(position).getId();
    }

    @Override
    public String getUrlSelectedUser() {
        DataProfile user = getSelectedUser();
        return user == null ? null : user.getImage(0);
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        DataProfile item = data.get(position);
        return item.isLikedAnyPhoto();
    }

    @Override
    public String getUserId(int position) {
        return data.get(position).getId();
    }

    @Override
    public void addListener(ICacheMessagesListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public String getUserSelectedID() {
        return selectedUserId;
    }

    @Override
    public void setUserSelected(String userId) {
        selectedUserId = userId;
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheMessagesListener listener = listeners.get(name);
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

    private DataProfile getSelectedUser() {
        if (data == null) return null;
        for (DataProfile item : data) {
            if (item.getId().equals(selectedUserId))
                return item;
        }
        return null;
    }

    @Override
    public void setSelected(int adapterPosition, int firstVisibleItemPosition) {
        data.get(adapterPosition).setSelected(firstVisibleItemPosition);
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return data.get(position).getSelectedPosition();
    }

}
