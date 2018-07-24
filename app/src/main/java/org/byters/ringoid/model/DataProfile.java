package org.byters.ringoid.model;

import java.util.ArrayList;

public class DataProfile {
    private ArrayList<DataImage> urls;

    public int getItemsNum() {
        return urls == null ? 0 : urls.size();
    }

    public String getImage(int imagePosition) {
        return urls.get(imagePosition).url;
    }

    public float getRatio(int imagePosition) {
        return urls.get(imagePosition).ratio;
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

    public float getRatioMax() {
        float max = 0;

        for (DataImage item : urls) {
            if (item.ratio > max)
                max = item.ratio;
        }
        return max;
    }

    private class DataImage {
        private String url;
        private float ratio;
        private int likes;
        private boolean isLiked;
    }
}
