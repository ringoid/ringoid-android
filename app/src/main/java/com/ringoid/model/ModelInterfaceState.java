package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class ModelInterfaceState implements Serializable {
    private String originPhotoId;
    private int currentPage;
    private int positionScrollPageLikes;

    public String getOriginPhotoId() {
        return originPhotoId;
    }

    public void setOriginPhotoId(String originPhotoId) {
        this.originPhotoId = originPhotoId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPositionScrollPageLikes(int positionScrollPageLikes) {
        this.positionScrollPageLikes = positionScrollPageLikes;
    }

    public int getPositionScrollPageLikes() {
        return positionScrollPageLikes;
    }
}
