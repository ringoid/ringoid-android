package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelProfilePhotos implements Serializable {
    private ArrayList<ProfilePhoto> data;

    public int size() {
        return data == null ? 0 : data.size();
    }

    public void add(ArrayList<ProfilePhoto> photos) {

        if (data == null) data = new ArrayList<>();
        dedublicate(data, photos);
    }

    private void dedublicate(ArrayList<ProfilePhoto> data, ArrayList<ProfilePhoto> photos) {
        Iterator<ProfilePhoto> iterator = data.iterator();
        while (iterator.hasNext()) {
            ProfilePhoto item = iterator.next();
            if (item == null) continue;
            ProfilePhoto photo = getPhotoWithSameLocalId(photos, item.getOriginPhotoId());
            if (photo == null) continue;
            photo.setPlaceholder(item.getPhotoUri());
            iterator.remove();
        }
        data.addAll(photos);
    }

    private ProfilePhoto getPhotoWithSameLocalId(ArrayList<ProfilePhoto> photos, String id) {
        if (photos == null) return null;
        for (ProfilePhoto item : photos)
            if (item.getOriginPhotoId() != null && item.getOriginPhotoId().equals(id))
                return item;
        return null;
    }

    public int getLikes(int position) {
        return data == null ? 0 : data.get(position).getLikes();
    }

    public String getPhotoUri(int position) {
        return data == null ? null : data.get(position).getPhotoUri();
    }

    public String getPhotoId(int position) {
        return data == null ? null : data.get(position).getPhotoId();
    }

    public boolean remove(ProfilePhoto item) {
        if (data == null) return false;
        return data.remove(item);
    }

    public void add(ProfilePhoto profilePhoto) {
        if (data == null) data = new ArrayList<>();
        data.add(0, profilePhoto);
    }

    public boolean remove(int index) {
        if (data == null) return false;
        data.remove(index);
        return true;
    }

    public boolean isEquals(int i, String imageId, String localId, String originId) {
        return data != null && data.get(i).isEquals(imageId, localId, originId);
    }

    public String getOriginPhotoId(int pos) {
        return data == null || pos < 0 || pos >= data.size() ? null : data.get(pos).getOriginPhotoId();
    }

    public int getPositionByOriginPhotoId(String originPhotoId, int defaultValue) {
        if (data == null) return defaultValue;
        for (int i = 0; i < data.size(); ++i)
            if (data.get(i).isEqualsOriginPhotoId(originPhotoId))
                return i;
        return defaultValue;
    }

    public ProfilePhoto getItemByClientPhotoId(String clientPhotoId) {
        if (data == null) return null;
        for (ProfilePhoto item : data)
            if (item.isEqualsClientPhotoId(clientPhotoId))
                return item;
        return null;
    }

    public boolean removeLocalPhotos() {
        boolean isRemoved = false;

        Iterator<ProfilePhoto> iterator = data.iterator();
        while (iterator.hasNext()) {
            ProfilePhoto item = iterator.next();
            if (item.isLocal()) {
                iterator.remove();
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    public String getUrlThumbnail(int position) {
        return data == null ? null : data.get(position).getPlaceholderUrl();
    }

    public String getPhotoLocalId(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position).getPhotoLocalId();
    }

    public String getPhotoOriginId(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position).getPhotoOriginId();
    }
}
