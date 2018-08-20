/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

import java.util.ArrayList;

public class DataProfile {
    private ArrayList<DataImage> urls;
    private String id;

    public String getId() {
        return id;
    }

    public int getItemsNum() {
        return urls == null ? 0 : urls.size();
    }

    public String getImage(int imagePosition) {
        return urls.get(imagePosition).url;
    }

    public int getLikes(int pos) {
        return urls.get(pos).likes;
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

    private class DataImage {
        private String url;
        private int likes;
        private boolean isLiked;
    }
}
