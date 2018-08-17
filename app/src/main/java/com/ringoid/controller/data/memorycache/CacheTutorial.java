/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

public class CacheTutorial implements ICacheTutorial {
    private boolean isLikesShown;

    public CacheTutorial() {
        resetLikes();
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
}
