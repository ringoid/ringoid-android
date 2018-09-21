package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;

public class ModelFeedMessages implements Serializable {
    private ArrayList<DataProfile> data;

    public ModelFeedMessages(ArrayList<DataProfile> data) {
        this.data = data;
    }

    public ModelFeedMessages() {

    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public DataProfile get(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position);
    }

    public DataProfile getUserByID(String selectedUserId) {
        if (data == null) return null;
        for (DataProfile item : data) {
            if (item.getId().equals(selectedUserId))
                return item;
        }
        return null;
    }
}
