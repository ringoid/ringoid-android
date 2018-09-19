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
        dedupblicate(data, photos);
    }

    private void dedupblicate(ArrayList<ProfilePhoto> data, ArrayList<ProfilePhoto> photos) {
        Iterator<ProfilePhoto> iterator = data.iterator();
        while (iterator.hasNext()) {
            ProfilePhoto item = iterator.next();
            if (isContains(photos, item))
                iterator.remove();
        }
        data.addAll(photos);
    }

    private boolean isContains(ArrayList<ProfilePhoto> photos, ProfilePhoto other) {
        if (photos == null) return false;
        for (ProfilePhoto item : photos)
            if (item.getOriginPhotoId().equals(other.getOriginPhotoId()))
                return true;
        return false;
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
        data.add(profilePhoto);
    }

    public boolean setPhotoLocalUploaded(String originPhotoId) {
        if (data == null) return false;
        for (ProfilePhoto item : data) {
            if (item.getOriginPhotoId().equals(originPhotoId)) {
                item.setStatusUploaded();
                return true;
            }
        }
        return false;
    }

    public ProfilePhoto getItem(String imageId) {
        if (data == null) return null;
        for (ProfilePhoto item : data)
            if (item.getPhotoId().equals(imageId))
                return item;
        return null;
    }

    public boolean isLocal(int position) {
        return data == null ? false : data.get(position).isLocal();
    }

    public boolean isUploading(int position) {
        return data == null ? false : data.get(position).isUploading();
    }

    public boolean remove(int index) {
        if (data == null) return false;
        data.remove(index);
        return true;
    }

    public boolean isEquals(int i, String imageId) {
        return data != null && data.get(i).getPhotoId().equals(imageId);

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
}
