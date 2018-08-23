package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class DataTutorial implements Serializable {

    public boolean isLikesShown;
    public boolean isExploreShown;
    public boolean isShowDialogProfileLikes;
    public boolean isShowDialogHiddenMode;
    public boolean isShowDialogExplore;
    public String imageLikesId;
    public int imageLikes;

    public DataTutorial() {
        isLikesShown = false;
        isExploreShown = false;
        isShowDialogHiddenMode = true;
        isShowDialogProfileLikes = true;
        isShowDialogExplore = true;
        imageLikes = 0;
        imageLikesId = null;
    }
}
