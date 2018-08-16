/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheLikes;

import javax.inject.Inject;

public class PresenterAdapterLikes implements IPresenterAdapterLikes {

    @Inject
    ICacheLikes cacheLikes;

    public PresenterAdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheLikes.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheLikes.getItemsNum(position);
    }
}
