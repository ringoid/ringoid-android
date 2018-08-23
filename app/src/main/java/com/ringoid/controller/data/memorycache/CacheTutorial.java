/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

public class CacheTutorial implements ICacheTutorial {

    //todo store in prererences

    private boolean isLikesShown;
    private boolean isExploreShown;
    private boolean isShowDialogProfileLikes;
    private boolean isShowDialogHiddenMode;
    private boolean isShowDialogExplore;
    private String imageLikesId;
    private int imageLikes;

    public CacheTutorial() {
        resetLikes();
        resetExplore();
        isShowDialogHiddenMode = true;
        isShowDialogProfileLikes = true;
        isShowDialogExplore = true;
        resetLikesNum();
    }

    @Override
    public void resetLikes() {
        this.isLikesShown = false;
    }

    @Override
    public boolean isLikesShown() {
        return isLikesShown;
    }

    @Override
    public void setLikesShown() {
        this.isLikesShown = true;
    }

    @Override
    public void resetExplore() {
        isExploreShown = false;
    }

    @Override
    public boolean isExploreShown() {
        return isExploreShown;
    }

    @Override
    public void setExploreShown() {
        isExploreShown = true;
    }

    @Override
    public void setProfileDialogLikeShow(boolean isShow) {
        this.isShowDialogProfileLikes = isShow;
    }

    @Override
    public boolean isShowDialogLikes() {
        return isShowDialogProfileLikes;
    }

    @Override
    public boolean isShowDialogHiddenMode() {
        return isShowDialogHiddenMode;
    }

    @Override
    public void setDialogHiddenModeShow(boolean isShow) {
        isShowDialogHiddenMode = isShow;
    }

    @Override
    public boolean isShowDialogExplore() {
        return isShowDialogExplore;
    }

    @Override
    public void setDialogExploreShow(boolean isShow) {
        isShowDialogExplore = isShow;
    }

    @Override
    public void resetLikesNum() {
        imageLikes = 0;
        imageLikesId = null;
    }

    @Override
    public void setLikesNum(String itemId) {
        if (imageLikesId != null && imageLikesId.equals(itemId)) {
            ++imageLikes;
            return;
        }

        imageLikes = 1;
        imageLikesId = itemId;
    }

    @Override
    public int getImageLikes() {
        return imageLikes;
    }
}
