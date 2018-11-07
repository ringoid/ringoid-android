package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.R;

import java.io.Serializable;

public class ModelInterfaceState implements Serializable {

    private String originPhotoId;
    private int currentPage;
    private int positionScrollPageLikes;
    private int positionScrollPageMessages;
    private int positionScrollPageExplore;
    private int positionScrollPageSettings;
    private int positionScrollPageLikesOffset;
    private int positionScrollPageMessagesOffset;
    private int positionScrollPageExploreOffset;
    private int positionScrollPageSettingsOffset;
    private int themeId;
    transient private boolean dialogComposeShow;

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

    public void setPositionScrollPageLikes(int positionScrollPageLikes, int offset) {
        this.positionScrollPageLikes = positionScrollPageLikes;
        this.positionScrollPageLikesOffset = offset;
    }

    public void setPositionScrollPageMessages(int position, int offset) {
        this.positionScrollPageMessages = position;
        this.positionScrollPageMessagesOffset = offset;
    }

    public void setPositionScrollPageExplore(int positionScrollPageExplore, int offset) {
        this.positionScrollPageExplore = positionScrollPageExplore;
        this.positionScrollPageExploreOffset = offset;
    }

    public void setPositionScrollPageSettings(int firstVisibleItemPosition, int offset) {
        this.positionScrollPageSettings = firstVisibleItemPosition;
        this.positionScrollPageSettingsOffset = offset;
    }

    public int getPositionScrollPageMessages() {
        return positionScrollPageMessages;
    }

    public int getPositionScrollPageLikes() {
        return positionScrollPageLikes;
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

    public int getPositionScrollPageSettings() {
        return positionScrollPageSettings;
    }

    public int getPositionScrollPageSettingsOffset() {
        return positionScrollPageSettingsOffset;
    }

    public void setDialogComposeShow(boolean dialogComposeShow) {
        this.dialogComposeShow = dialogComposeShow;
    }

    public boolean isDialogComposeShown() {
        return dialogComposeShow;
    }

    public void updateTheme() {
        themeId = themeId == R.style.AppThemeLight ? R.style.AppThemeDark : R.style.AppThemeLight;
    }

    public int getThemeId() {
        return themeId;
    }
}
