package com.ringoid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelPhotoRemove implements Serializable {
    private ArrayList<PhotoRemove> data;

    public int size() {
        return data == null ? 0 : data.size();
    }

    public PhotoRemove get(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position);
    }

    public boolean remove(String photoId, String originId) {
        if (data == null) return false;

        Iterator<PhotoRemove> itr = data.iterator();

        while (itr.hasNext()) {
            PhotoRemove item = itr.next();

            if (item.originId != null && item.originId.equals(originId)
                    || item.photoId != null && item.photoId.equals(photoId)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    public void add(PhotoRemove item) {
        if (data == null) data = new ArrayList<>();
        data.add(item);
    }

    public boolean isContains(String photoId) {
        if (data == null) return false;
        for (PhotoRemove item : data)
            if (item.photoId != null && item.photoId.equals(photoId)) return true;
        return false;
    }
}
