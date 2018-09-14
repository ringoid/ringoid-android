package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;

public class ModelChat implements Serializable {

    private ArrayList<ChatInstance> data;

    public void add(DataMessage dataMessage, String userSelectedID) {
        if (data == null) data = new ArrayList<>();

        ChatInstance item = getItem(userSelectedID);
        if (item == null) {
            item = new ChatInstance(userSelectedID);
            data.add(item);
        }
        item.add(dataMessage);
    }

    private ChatInstance getItem(String userSelectedID) {
        if (data == null) return null;
        for (ChatInstance item : data)
            if (item.isWithUser(userSelectedID))
                return item;
        return null;
    }

    public int size(String userId) {
        ChatInstance item = getItem(userId);
        if (item == null) return 0;
        return item.size();

    }

    public boolean isSelf(String userSelectedID, int position) {
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return false;
        return item.isSelf(position);
    }

    public String getMessage(String userSelectedID, int position) {
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return null;
        return item.getMessage(position);
    }

    public void resetCache(String userSelectedID) {
        if (data == null) return;
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return;
        data.remove(item);
    }

    public boolean isReaded(String userId) {
        ChatInstance item = getItem(userId);
        if (item == null) return false;
        return item.isReaded();
    }

    public void setReaded(String userSelectedID) {
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return;
        item.setReaded();
    }
}
