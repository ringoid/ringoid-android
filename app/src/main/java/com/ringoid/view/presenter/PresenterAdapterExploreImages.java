/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterExploreImages implements IPresenterAdapterExploreImages {

    private static int MAX_LIKES_DIALOG_SHOW = 3;

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    IViewPopup viewPopup;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    IViewDialogs viewDialogs;

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
        return cacheExplore.getUrl(adapterPosition, itemPosition);
    }

    @Override
    public void onLongClick(int adapterPosition, int itemPosition) {


    }

    @Override
    public boolean onClickIconLike(int adapterPosition, int itemPosition) {
        cacheExplore.changeLiked(adapterPosition, itemPosition);
        return cacheExplore.isLiked(adapterPosition, itemPosition);
    }
}
