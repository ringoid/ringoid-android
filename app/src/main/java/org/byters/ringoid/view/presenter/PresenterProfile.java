package org.byters.ringoid.view.presenter;

import android.support.v7.app.AppCompatActivity;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheProfile;
import org.byters.ringoid.view.INavigator;

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
