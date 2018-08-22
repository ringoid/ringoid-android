/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

public interface ICacheTutorial {
    void resetLikes();

    boolean isLikesShown();

    void setLikesShown();

    void resetExplore();

    boolean isExploreShown();

    void setExploreShown();

    void setProfileDialogLikeShow(boolean isShow);

    boolean isShowDialogLikes();
}
