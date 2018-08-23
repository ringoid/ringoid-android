/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataTutorial;

import javax.inject.Inject;

public class CacheTutorial implements ICacheTutorial {

    @Inject
    ICacheStorage cacheStorage;

    private DataTutorial dataTutorial;

    public CacheTutorial() {
        ApplicationRingoid.getComponent().inject(this);
    }

    private DataTutorial getData() {
        if (dataTutorial == null)
            dataTutorial = cacheStorage.readObject(FileEnum.TUTORIAL, DataTutorial.class);

        if (dataTutorial == null) dataTutorial = new DataTutorial();
        return dataTutorial;
    }

    @Override
    public boolean isLikesShown() {
        return getData().isLikesShown;
    }

    @Override
    public void setLikesShown() {
        getData().isLikesShown = true;
        updateData();
    }

    @Override
    public boolean isExploreShown() {
        return getData().isExploreShown;
    }

    @Override
    public void setExploreShown() {
        getData().isExploreShown = true;
        updateData();
    }

    @Override
    public void setProfileDialogLikeShow(boolean isShow) {
        getData().isShowDialogProfileLikes = isShow;
        updateData();
    }

    @Override
    public boolean isShowDialogLikes() {
        return getData().isShowDialogProfileLikes;
    }

    @Override
    public boolean isShowDialogHiddenMode() {
        return getData().isShowDialogHiddenMode;
    }

    @Override
    public void setDialogHiddenModeShow(boolean isShow) {
        getData().isShowDialogHiddenMode = isShow;
        updateData();
    }

    @Override
    public boolean isShowDialogExplore() {
        return getData().isShowDialogExplore;
    }

    @Override
    public void setDialogExploreShow(boolean isShow) {
        getData().isShowDialogExplore = isShow;
        updateData();
    }

    @Override
    public void resetLikesNum() {
        getData().imageLikes = 0;
        getData().imageLikesId = null;
        updateData();
    }

    @Override
    public void setLikesNum(String itemId) {
        if (getData().imageLikesId != null && getData().imageLikesId.equals(itemId)) {
            increaseImageLikes();
            return;
        }

        getData().imageLikes = 1;
        getData().imageLikesId = itemId;
        updateData();
    }

    private void increaseImageLikes() {
        ++getData().imageLikes;
        updateData();
    }

    private void updateData() {
        cacheStorage.writeData(FileEnum.TUTORIAL, dataTutorial);
    }

    @Override
    public int getImageLikes() {
        return getData().imageLikes;
    }

    @Override
    public void resetCache() {
        dataTutorial = null;
        cacheStorage.removeData(FileEnum.TUTORIAL);
    }
}
