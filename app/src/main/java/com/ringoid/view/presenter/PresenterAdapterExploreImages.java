/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterExploreImages implements IPresenterAdapterExploreImages {

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
        checkLikesTutorial(adapterPosition, itemPosition);
        checkLikedAlready(adapterPosition, itemPosition);
        cacheExplore.setLiked(adapterPosition, itemPosition);
        cacheTutorial.setLikesNum(cacheExplore.getItemId(adapterPosition, itemPosition));
    }

    private void checkLikedAlready(int adapterPosition, int itemPosition) {
        if (!cacheExplore.isLiked(adapterPosition, itemPosition)) return;
        viewPopup.showToast(R.string.message_explore_like_other);
    }

    private void checkLikesTutorial(int adapterPosition, int itemPosition) {
        if (cacheTutorial.isExploreShown() || cacheExplore.isLiked(adapterPosition, itemPosition))
            return;
        cacheTutorial.setExploreShown();
        viewPopup.showToast(R.string.message_explore_like_tutorial);
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return cacheExplore.isLiked(adapterPosition, itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return "file:///android_asset/" + cacheExplore.getUrl(adapterPosition, itemPosition);
    }

    @Override
    public void onLongClick(int adapterPosition, int itemPosition) {
        if (cacheTutorial.isShowDialogExplore()) {
            viewDialogs.showDialogExplore();
            return;
        }

        viewPopup.showToast(R.string.message_explore_like_other);
    }

    @Override
    public boolean onClickIconLike(int adapterPosition, int itemPosition) {
        cacheExplore.changeLiked(adapterPosition, itemPosition);
        return cacheExplore.isLiked(adapterPosition, itemPosition);
    }

    @Override
    public boolean isLikeable() {
        return !cacheSettingsPrivacy.isPrivacyPhotosNoone();
    }
}
