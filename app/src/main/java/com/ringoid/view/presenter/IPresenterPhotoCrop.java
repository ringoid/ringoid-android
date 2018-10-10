package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;

import java.io.File;

public interface IPresenterPhotoCrop {
    void onCropCompleted(File file);

    void setListener(IPresenterPhotoCropListener listener);

    void onCLickCrop();

    boolean onBackPressed();

}
