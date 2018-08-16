package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;

import javax.inject.Inject;

public class PresenterAdapterExplore implements IPresenterAdapterExplore {

    @Inject
    ICacheExplore cacheExplore;

    public PresenterAdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheExplore.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheExplore.getItemsNum(position);
    }
}
