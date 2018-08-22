/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

public class CacheTutorial implements ICacheTutorial {

    //todo store in prererences

    private boolean isLikesShown;
    private boolean isExploreShown;
    private boolean isShowDialogProfileLikes;
    private boolean isShowDialogHiddenMode;

    public CacheTutorial() {
        resetLikes();
        resetExplore();
        isShowDialogHiddenMode = true;
        isShowDialogProfileLikes = true;
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
}
