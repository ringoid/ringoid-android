package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;

import javax.inject.Inject;

public class PresenterAdapterExploreImages implements IPresenterAdapterExploreImages {

    @Inject
    ICacheExplore cacheExplore;

    public PresenterAdapterExploreImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return cacheExplore.getItemsNum(adapterPosition);
    }

    @Override
    public void onClickLike(int adapterPosition, int itemPosition) {
        cacheExplore.setLiked(adapterPosition, itemPosition);
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return cacheExplore.isLiked(adapterPosition, itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return "file:///android_asset/" +cacheExplore.getUrl(adapterPosition, itemPosition);
    }
}
