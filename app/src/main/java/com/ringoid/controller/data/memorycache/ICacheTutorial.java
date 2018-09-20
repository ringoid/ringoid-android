/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

public interface ICacheTutorial {

    boolean isShowDialogLikes();

    boolean isShowDialogHiddenMode();

    void setDialogHiddenModeShow(boolean isShow);

    boolean isShowDialogExplore();

    void resetLikesNum();

    void resetCache();
}
