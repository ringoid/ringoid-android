/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
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

    public PresenterAdapterLikesImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return cacheLikes.getItemsNum(adapterPosition);
    }

    @Override
    public void onClickLike(int adapterPosition, int itemPosition) {
        checkLikedAlready(adapterPosition, itemPosition);
        cacheLikes.setLiked(adapterPosition, itemPosition);
        checkLikesTutorial(adapterPosition, itemPosition);
        checkLikesDialog(adapterPosition, itemPosition);
    }

    private void checkLikesDialog(int adapterPosition, int itemPosition) {
        cacheTutorial.setLikesNum(cacheLikes.getItemId(adapterPosition, itemPosition));

        if (cacheTutorial.getImageLikes() < MAX_LIKES_DIALOG_SHOW) return;

        cacheTutorial.resetLikesNum();
        viewDialogs.showDialogLikes();
    }

    private void checkLikedAlready(int adapterPosition, int itemPosition) {
        if (!cacheLikes.isLiked(adapterPosition, itemPosition)) return;
        viewPopup.showToast(R.string.message_likes_other);
    }

    private void checkLikesTutorial(int adapterPosition, int itemPosition) {
        if (cacheTutorial.isLikesShown() || cacheLikes.isLiked(adapterPosition, itemPosition))
            return;
        cacheTutorial.setLikesShown();
        viewDialogs.showDialogLikes();
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

        viewPopup.showToast(R.string.message_likes_other);

    }

    @Override
    public boolean onClickIconLike(int adapterPosition, int itemPosition) {
        cacheLikes.changeLiked(adapterPosition, itemPosition);
        return cacheLikes.isLiked(adapterPosition, itemPosition);
    }
}
