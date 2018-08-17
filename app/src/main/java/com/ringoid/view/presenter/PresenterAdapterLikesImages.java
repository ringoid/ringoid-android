/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterLikesImages implements IPresenterAdapterLikesImages {

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    IViewPopup viewPopup;

    public PresenterAdapterLikesImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return cacheLikes.getItemsNum(adapterPosition);
    }

    @Override
    public void onClickLike(int adapterPosition, int itemPosition) {
        checkLikesTutorial(adapterPosition, itemPosition);
        cacheLikes.setLiked(adapterPosition, itemPosition);
    }

    private void checkLikesTutorial(int adapterPosition, int itemPosition) {
        if (cacheTutorial.isLikesShown() || cacheLikes.isLiked(adapterPosition, itemPosition))
            return;
        cacheTutorial.setLikesShown();
        viewPopup.showToast(R.string.message_likes_tutorial);
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return cacheLikes.isLiked(adapterPosition, itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return "file:///android_asset/" + cacheLikes.getUrl(adapterPosition, itemPosition);
    }
}
