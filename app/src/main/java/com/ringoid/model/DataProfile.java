/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

import com.ringoid.controller.data.network.response.ResponseNewFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public class DataImage implements Serializable {
        private String url;
        private String id;
        private boolean isLiked;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isLiked() {
            return isLiked;
        }

        public void setLiked(boolean liked) {
            isLiked = liked;
        }
    }

    public ArrayList<DataImage> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<DataImage> urls) {
        this.urls = urls;
    }

    public void setUrls(List<ResponseNewFaces.Photos> urls) {
        ArrayList<DataImage> dataImageArrayList = new ArrayList<>();
        for(int i=0;i<urls.size();i++){
            DataImage dataImage = new DataImage();
            dataImage.setId(urls.get(i).getPhotoId());
            dataImage.setUrl(urls.get(i).getPhotoUri());
            dataImageArrayList.add(dataImage);
        }

        this.urls = dataImageArrayList;
    }
}
