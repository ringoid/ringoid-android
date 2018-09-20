package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class ModelInterfaceState implements Serializable {
    private String originPhotoId;
    private int currentPage;
    private int positionScrollPageLikes;
    private int positionScrollPageMessages;
    private int positionScrollPageExplore;

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

    public int getPositionScrollPageLikes() {
        return positionScrollPageLikes;
    }

    public void setPositionScrollPageLikes(int positionScrollPageLikes) {
        this.positionScrollPageLikes = positionScrollPageLikes;
    }

    public int getPositionScrollPageMessages() {
        return positionScrollPageMessages;
    }

    public void setPositionScrollPageMessages(int position) {
        this.positionScrollPageMessages = position;
    }

    public void setPositionScrollPageExplore(int positionScrollPageExplore) {
        this.positionScrollPageExplore = positionScrollPageExplore;
    }

    public int getPositionScrollPageExplore() {
        return positionScrollPageExplore;
    }
}
