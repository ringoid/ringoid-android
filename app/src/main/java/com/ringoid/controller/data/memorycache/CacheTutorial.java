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

    @Override
    public boolean isShowDialogPhotoAdded() {
        return getData().isShowDialogPhotoAdded();
    }

    @Override
    public void setShowDialogPhotoAdded(boolean isShow) {
        getData().setShowDialogPhotoAdded(isShow);
        updateData();
    }
}
