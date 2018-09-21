/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DataProfile implements Serializable {
    private ArrayList<DataImage> urls;
    private String id;
    private int selectedPosition;

    public String getId() {
        return id;
    }

    public int getItemsNum() {
        return urls == null ? 0 : urls.size();
    }

    public String getImage(int imagePosition) {
        return urls.get(imagePosition).url;
    }

    public boolean isLiked(int pos) {
        return urls.get(pos).isLiked;
    }

    public void setLiked(int itemPosition) {
        urls.get(itemPosition).isLiked = true;
    }

    public void changeLiked(int itemPosition) {
        urls.get(itemPosition).isLiked = !urls.get(itemPosition).isLiked;
    }

    public String getImageId(int itemPosition) {
        return urls.get(itemPosition).getId();
    }

    public boolean isLikedAnyPhoto() {
        if (urls == null) return false;
        for (DataImage item : urls)
            if (item.isLiked)
                return true;
        return false;
    }

    public void setSelected(int firstVisibleItemPosition) {
        this.selectedPosition = firstVisibleItemPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    private class DataImage implements Serializable {
        private String url;
        private String id;
        private boolean isLiked;

        public String getId() {
            return id;
        }
    }
}
