package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;

class ChatInstance implements Serializable {

    private String userId;
    private ArrayList<DataMessage> data;

    ChatInstance(String userSelectedID) {
        this.userId = userSelectedID;
    }

    public void add(DataMessage dataMessage) {
        if (data == null) data = new ArrayList<>();
        data.add(dataMessage);
    }

    public boolean isWithUser(String userSelectedID) {
        return userId.equals(userSelectedID);
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public String getMessage(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position).getMessage();
    }

    public boolean isSelf(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position).isSelf();
    }
}
