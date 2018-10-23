/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataProfile;
import com.ringoid.model.ModelFeedMessages;

import java.util.ArrayList;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheMessages implements ICacheMessages {

    @Inject
    ICacheStorage cacheStorage;

    private ModelFeedMessages data;
    private DataProfile selectedUser;

    private WeakHashMap<String, ICacheMessagesListener> listeners;

    public CacheMessages() {
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
    public String getUrl(int adapterPosition, int itemPosition) {
        return getData().get(adapterPosition).getImage(itemPosition);
    }

    @Override
    public String getUrlSelectedUser() {
        return selectedUser == null ? null : selectedUser.getImage(selectedUser.getSelectedPosition());
    }

    @Override
    public boolean isDataExist() {
        return getData() != null && getData().size() > 0;
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        DataProfile item = getData().get(position);
        return item.isLikedAnyPhoto();
    }

    @Override
    public String getUserId(int position) {
        return getData().get(position).getId();
    }

    @Override
    public void addListener(ICacheMessagesListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public String getUserSelectedID() {
        return selectedUser == null ? null : selectedUser.getId();
    }

    @Override
    public void setUserSelected(DataProfile user) {
        selectedUser = user;
        notifyListenersUserSelectedChange();
    }

    private void notifyListenersUserSelectedChange() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheMessagesListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onSelectedUserUpdate(getUserSelectedID());
        }
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            ICacheMessagesListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.onUpdate();
        }
    }

    @Override
    public void setSelected(int adapterPosition, int firstVisibleItemPosition) {
        getData().get(adapterPosition).setSelected(firstVisibleItemPosition);
        saveData();
    }

    private ModelFeedMessages getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_FEED_MESSAGES, ModelFeedMessages.class);
        if (data == null) data = new ModelFeedMessages();
        return data;
    }

    @Override
    public void setData(ArrayList<DataProfile> data) {
        this.data = new ModelFeedMessages(data);
        saveData();
        notifyListeners();
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return getData().get(position).getSelectedPosition();
    }

    @Override
    public void resetCache() {
        this.data = null;
        cacheStorage.removeData(FileEnum.CACHE_FEED_MESSAGES);
    }

    @Override
    public int getPosition(String userSelectedID, int noValue) {
        return getData().getPosition(userSelectedID, noValue);
    }

    @Override
    public DataProfile getUser(int position) {
        return getData().get(position);
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_FEED_MESSAGES, data);
    }

}
