package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class DataTutorial implements Serializable {

    public String imageLikesId;
    public int imageLikes;
    private boolean showDialogPhotoAdded;

    public DataTutorial() {
        imageLikes = 0;
        imageLikesId = null;
        showDialogPhotoAdded = true;
    }

    public boolean isShowDialogPhotoAdded() {
        return showDialogPhotoAdded;
    }

    public void setShowDialogPhotoAdded(boolean isShow) {
        showDialogPhotoAdded = isShow;
    }
}
