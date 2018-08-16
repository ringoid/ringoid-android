/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.app.AppCompatActivity;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.view.INavigator;

import javax.inject.Inject;

public class PresenterProfile implements IPresenterProfile {

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    INavigator navigator;

    public PresenterProfile() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public void onClickPhotoAdd(AppCompatActivity activity) {
        navigator.navigatePhotoAdd(activity);
    }

}
