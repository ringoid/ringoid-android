package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.presenter.util.IHelperConnection;
import com.ringoid.view.presenter.util.IHelperPhotoUpload;
import com.ringoid.view.ui.dialog.callback.IDialogPhotoUploadedFirstListener;

import java.io.File;
import java.lang.ref.WeakReference;

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

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    IHelperPhotoUpload helperPhotoUpload;

    private IDialogPhotoUploadedFirstListener listenerDialogPhotoUpload;

    private WeakReference<IPresenterPhotoCropListener> refListener;

    public PresenterPhotoCrop() {
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogPhotoUpload = new DialogPhotoUploadedFirstListener();
    }

    @Override
    public void onCropCompleted(File file) {

        if (file.length() > MAX_FILE_SIZE_BYTES) {
            viewDialogs.showDialogMessage(R.string.error_photo_max_size);
            return;
        }

        cacheInterfaceState.resetOriginPhotoId();
        helperPhotoUpload.addPhotoLocal(file);

        checkDialogPhotoAdd();

        onBackPressed();
    }

    private void checkDialogPhotoAdd() {
        if (!cacheTutorial.isShowDialogPhotoAdded()) return;
        cacheTutorial.setShowDialogPhotoAdded(false);
        viewDialogs.showDialogPhotoUploadedFirst(listenerDialogPhotoUpload);
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
        cacheTutorial.setShowDialogPhotoAdded(false);
        cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_PROFILE);
        navigator.navigateFeed();
        return true;
    }

    private class DialogPhotoUploadedFirstListener implements IDialogPhotoUploadedFirstListener {

        @Override
        public void onSelectFeed() {
            cacheInterfaceState.setCurrentPage(PresenterPagesContainer.INDEX_PAGE_EXPLORE);
            navigator.navigateFeed();
        }

        @Override
        public void onSelectUploadPhoto() {
            navigator.navigatePhotoAdd();
            cacheTutorial.setShowDialogPhotoAdded(true);
        }
    }
}
