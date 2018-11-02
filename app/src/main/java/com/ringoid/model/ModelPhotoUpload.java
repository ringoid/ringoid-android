package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelPhotoUpload implements Serializable {
    private ArrayList<PhotoUpload> data;

    public void add(PhotoUpload item) {
        if (data == null) data = new ArrayList<>();
        data.add(0, item);
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public PhotoUpload getItem(int pos) {
        return data == null || pos < 0 || pos >= data.size() ? null : data.get(pos);
    }

    public boolean remove(String photoClientId) {
        if (data == null) return false;

        Iterator<PhotoUpload> iterator = data.iterator();
        while (iterator.hasNext()) {
            PhotoUpload item = iterator.next();
            if (item == null) continue;
            if (item.getClientPhotoId().equals(photoClientId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isContains(String localId) {
        if (data == null || localId == null) return false;
        for (PhotoUpload item : data)
            if (item.getClientPhotoId().equals(localId))
                return true;
        return false;
    }
}
