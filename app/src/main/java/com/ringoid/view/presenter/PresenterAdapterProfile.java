package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheProfile;

import javax.inject.Inject;

public class PresenterAdapterProfile implements IPresenterAdapterProfile {

    @Inject
    ICacheProfile cacheProfile;

    public PresenterAdapterProfile() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public String getUrl(int pos) {
        return "file:///android_asset/" + cacheProfile.getImage(pos);
    }

    @Override
    public int getLikesNum(int position) {
        return cacheProfile.getLikesNum(position);
    }
}
