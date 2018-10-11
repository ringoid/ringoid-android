package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.repository.RepositoryPhotoUploadUri;
import com.ringoid.model.PhotoUpload;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.presenter.util.IHelperConnection;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.UUID;

import javax.inject.Inject;

public class PresenterPhotoCrop implements IPresenterPhotoCrop {

    private static long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IHelperConnection helperConnection;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    INavigator navigator;

    private WeakReference<IPresenterPhotoCropListener> refListener;

    public PresenterPhotoCrop() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onCropCompleted(File file) {

        if (file.length() > MAX_FILE_SIZE_BYTES) {
            viewDialogs.showDialogMessage(R.string.error_photo_max_size);
            return;
        }

        PhotoUpload photoUpload = new PhotoUpload();
        photoUpload.setFile(file);
        photoUpload.setClientPhotoID(UUID.randomUUID().toString());

        cacheProfile.addPhotoLocal(photoUpload.getFileUri(), photoUpload.getClientPhotoId());

        new RepositoryPhotoUploadUri(photoUpload).request();
        onBackPressed();
    }

    @Override
    public void setListener(IPresenterPhotoCropListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCLickCrop() {
        if (!helperConnection.isConnectionExist()) {
            navigator.navigateErrorConnection();
            return;
        }

        refListener.get().crop();
    }

    @Override
    public boolean onBackPressed() {
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_PROFILE);
        navigator.navigateFeed();
        return true;
    }
}
