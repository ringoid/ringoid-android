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
    public void resetLikesNum() {
        getData().imageLikes = 0;
        getData().imageLikesId = null;
        updateData();
    }

    private void updateData() {
        cacheStorage.writeData(FileEnum.TUTORIAL, dataTutorial);
    }

    @Override
    public void resetCache() {
        dataTutorial = null;
        cacheStorage.removeData(FileEnum.TUTORIAL);
    }
}
