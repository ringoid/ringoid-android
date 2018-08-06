package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import org.byters.ringoid.model.DataMessage;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class CacheChatMessages implements ICacheChatMessages {

    public static final String SMILE_SHY = ":wink:";
    public static final String SMILE_LOVE = ":love:";
    public static final String SMILE_KISS = ":kiss:";
    public static final String SMILE_HEART = ":heart:";

    private WeakHashMap<String, ICacheChatMessagesListener> listeners;

    private ArrayList<DataMessage> data;
    private boolean isSendEnabled;

    public CacheChatMessages() {
        resetCache();
    }

    @Override
    public void resetCache() {
        isSendEnabled = false;
        data = null;
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
    public void addMessage(String message) {
        if (data == null) data = new ArrayList<>();
        data.add(new DataMessage(true, message, "file:///android_asset/m1/01.jpg"));
        data.add(new DataMessage(false, "test", "file:///android_asset/f1/01.jpg"));
        isSendEnabled = true;
        notifyListeners();
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    @Override
    public boolean isSendEnabled() {
        return isSendEnabled;
    }

    @Override
    public void addListener(ICacheChatMessagesListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getSimpleName(), listener);
    }

    @Override
    public int getDataSize() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isSelf(int position) {
        return data == null || position >= data.size() || position < 0 ? false : data.get(position).isSelf();
    }

    @Override
    public String getUrl(int position) {
        return data == null || position >= data.size() || position < 0 ? null : data.get(position).getUrl();
    }

    @Override
    public String getMessage(int position) {
        return data == null || position >= data.size() || position < 0 ? null : data.get(position).getMessage();
    }
}
