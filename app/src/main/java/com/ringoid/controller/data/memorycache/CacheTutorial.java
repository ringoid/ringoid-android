/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

public class CacheTutorial implements ICacheTutorial {
    private boolean isLikesShown;
    private boolean isExploreShown;

    public CacheTutorial() {
        resetLikes();
        resetExplore();
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
}
