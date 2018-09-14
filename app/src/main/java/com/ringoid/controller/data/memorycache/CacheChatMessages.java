/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.support.annotation.NonNull;

import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataMessage;
import com.ringoid.model.ModelChat;

import java.util.Random;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheChatMessages implements ICacheChatMessages {

    @Inject
    Random random;

    @Inject
    ICacheStorage cacheStorage;

    private WeakHashMap<String, ICacheChatMessagesListener> listeners;
    private ModelChat data;

    public CacheChatMessages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.CHAT_CACHE);
        notifyListeners();
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheChatMessagesListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onDataChange();
        }
    }

    @Override
    public void addMessage(String userSelectedID, String message) {
        getData().add(new DataMessage(true, message), userSelectedID);

        if (random.nextBoolean() && BuildConfig.DEBUG)
            getData().add(new DataMessage(false, "test"), userSelectedID);
        cacheStorage.writeData(FileEnum.CHAT_CACHE, data);
        notifyListeners();
    }

    @NonNull
    private ModelChat getData() {
        if (data == null) data = cacheStorage.readObject(FileEnum.CHAT_CACHE, ModelChat.class);
        if (data == null) data = new ModelChat();
        return data;
    }

    @Override
    public boolean isDataExist(String userSelectedID) {
        return getData().size(userSelectedID) > 0;
    }

    @Override
    public void addListener(ICacheChatMessagesListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getSimpleName(), listener);
    }

    @Override
    public int getDataSize(String userId) {
        return getData().size(userId);
    }

    @Override
    public boolean isSelf(String userSelectedID, int position) {
        return position >= getData().size(userSelectedID) || position < 0 ? false : data.isSelf(userSelectedID, position);
    }

    @Override
    public String getMessage(String userSelectedID, int position) {
        return position >= getData().size(userSelectedID) || position < 0 ? null : data.getMessage(userSelectedID,position);
    }

    @Override
    public void resetCache(String userSelectedID) {
        getData().resetCache(userSelectedID);
    }
}
