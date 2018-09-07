/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.app.AppCompatActivity;

public interface IPresenterProfile {
    int getItemsNum();

    void onClickPhotoAdd(AppCompatActivity activity);

    void onClickSettings();


    void onClickToolbar();
}
