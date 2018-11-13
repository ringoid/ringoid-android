package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.R;
import com.ringoid.view.PAGE_ENUM;

import java.io.Serializable;

public class ModelInterfaceState implements Serializable {

    private String originPhotoId;
    private PAGE_ENUM currentPage;

    private int positionScrollPageLikes;
    private int positionScrollPageMessages;
    private int positionScrollPageExplore;
    private int positionScrollPageSettings;
    private int positionScrollPageLikesOffset;
    private int positionScrollPageMessagesOffset;
    private int positionScrollPageExploreOffset;
    private int positionScrollPageSettingsOffset;
    private int positionScrollPageMatches;
    private int positionScrollPageMatchesOffset;

    private int themeId;
    transient private boolean dialogComposeShow;
    private PAGE_ENUM pageLikes;
    private String phoneCode;
    private String phone;

    public String getOriginPhotoId() {
        return originPhotoId;
    }

    public void setOriginPhotoId(String originPhotoId) {
        this.originPhotoId = originPhotoId;
    }

    public PAGE_ENUM getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(PAGE_ENUM currentPage) {
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


    public void setPositionScrollPageMatches(int position, int offset) {
        this.positionScrollPageMatches = position;
        this.positionScrollPageMatchesOffset = offset;
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
        themeId = themeId == R.style.AppThemeLight || themeId == 0 ? R.style.AppThemeDark : R.style.AppThemeLight;
    }

    public void setThemeDark(boolean isChecked) {
        themeId = isChecked ? R.style.AppThemeDark : R.style.AppThemeLight;
    }

    public int getThemeId() {
        return themeId;
    }

    public PAGE_ENUM getPageLikes() {
        return pageLikes;
    }

    public void setPageLikes(PAGE_ENUM page) {
        this.pageLikes = page;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String s) {
        this.phone = s;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String s) {
        this.phoneCode = s;
    }

}
