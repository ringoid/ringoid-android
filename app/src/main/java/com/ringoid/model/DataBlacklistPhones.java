package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;

public class DataBlacklistPhones implements Serializable {
    private ArrayList<DataBlacklistPhone> data;

    public void add(DataBlacklistPhone dataBlacklistPhone) {
        if (data == null) data = new ArrayList<>();
        data.add(dataBlacklistPhone);
    }

    public boolean isContains(String code, String phone) {
        if (data == null) return false;
        for (DataBlacklistPhone item : data)
            if (item.isPhoneEquals(code, phone)) return true;
        return false;
    }

    public void remove(int position) {
        if (data == null || position < 0 || position >= data.size()) return;
        data.remove(position);
    }

    public DataBlacklistPhone get(int position) {
        return data == null || position < 0 || data.size() <= position ? null : data.get(position);
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public boolean remove(DataBlacklistPhone phone) {
        return data != null && data.remove(phone);
    }
}
