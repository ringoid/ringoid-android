/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterLikesImages implements IPresenterAdapterLikesImages {

    private static int MAX_LIKES_DIALOG_SHOW = 3;

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    IViewPopup viewPopup;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    INavigator navigator;

    @Inject
    ICacheMessages cacheMessages;

    public PresenterAdapterLikesImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return cacheLikes.getItemsNum(adapterPosition);
    }

    @Override
    public void onClickLike(int adapterPosition, int itemPosition) {
        cacheLikes.setLiked(adapterPosition, itemPosition);
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return cacheLikes.isLiked(adapterPosition, itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return "file:///android_asset/" + cacheLikes.getUrl(adapterPosition, itemPosition);
    }

    @Override
    public void onLongClick(int adapterPosition, int itemPosition) {
        if (cacheTutorial.isShowDialogLikes()) {
            viewDialogs.showDialogLikes();
            return;
        }



    }

    @Override
    public boolean onClickIconLike(int adapterPosition, int itemPosition) {
        cacheLikes.changeLiked(adapterPosition, itemPosition);
        return cacheLikes.isLiked(adapterPosition, itemPosition);
    }
}
