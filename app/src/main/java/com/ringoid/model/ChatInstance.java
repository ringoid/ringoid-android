package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;

class ChatInstance implements Serializable {

    private String userId;
    private ArrayList<DataMessage> data;
    private boolean isReaded;
    private ScrollSavedPosition scrollSavedPosition;

    ChatInstance(String userSelectedID) {
        this.userId = userSelectedID;
    }

    public void add(DataMessage dataMessage) {
        resetSavedPosition();
        if (data == null) data = new ArrayList<>();
        data.add(0, dataMessage);
        if (!dataMessage.isSelf()) isReaded = false;
    }

    private void resetSavedPosition() {
        scrollSavedPosition = null;
    }

    public boolean isWithUser(String userSelectedID) {
        return userId != null && userId.equals(userSelectedID);
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

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded() {
        isReaded = true;
    }

    public String getScrollSavedMessageId() {
        return scrollSavedPosition == null ? null : scrollSavedPosition.messageId;
    }

    public int getPosition(String messageId) {
        if (data == null) return 0;
        for (int i = 0; i < data.size(); ++i)
            if (data.get(i).getId().equals(messageId))
                return i;
        return 0;
    }

    public int getScrollSavedOffset() {
        return scrollSavedPosition == null ? 0 : scrollSavedPosition.offset;
    }

    public void setScrollSavedPostion(int pos, int offset) {
        String messageid = getMessageId(pos);
        if (messageid == null) return;
        scrollSavedPosition = new ScrollSavedPosition();
        scrollSavedPosition.messageId = messageid;
        scrollSavedPosition.offset = offset;
    }

    private String getMessageId(int pos) {
        return data == null || pos < 0 || pos >= data.size() ? null : data.get(pos).getId();
    }
}
