package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class ModelInterfaceState implements Serializable {
    private String originPhotoId;
    private int currentPage;
    private int positionScrollPageLikes;
    private int positionScrollPageMessages;
    private int positionScrollPageExplore;
    private int positionScrollPageLikesOffset;
    private int positionScrollPageMessagesOffset;
    private int positionScrollPageExploreOffset;

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

    public void setPositionScrollPageLikes(int positionScrollPageLikes, int offset) {
        this.positionScrollPageLikes = positionScrollPageLikes;
        this.positionScrollPageLikesOffset = offset;
    }

    public int getPositionScrollPageMessages() {
        return positionScrollPageMessages;
    }

    public void setPositionScrollPageMessages(int position, int offset) {
        this.positionScrollPageMessages = position;
        this.positionScrollPageMessagesOffset = offset;
    }

    public void setPositionScrollPageExplore(int positionScrollPageExplore, int offset) {
        this.positionScrollPageExplore = positionScrollPageExplore;
        this.positionScrollPageExploreOffset = offset;
    }

    public int getPositionScrollPageExplore() {
        return positionScrollPageExplore;
    }

    public int getPositionScrollPageExploreOffset() {
        return positionScrollPageExploreOffset;
    }

    public int getPositionScrollPageLikesOffset() {
        return positionScrollPageLikesOffset;
    }

    public int getPositionScrollPageMessagesOffset() {
        return positionScrollPageMessagesOffset;
    }
}
