package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.repository.IRepositoryPhotoUpload;
import com.ringoid.controller.data.repository.IRepositoryPhotoUploadUri;
import com.ringoid.controller.data.repository.callback.IRepositoryPhotoUploadListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.presenter.util.IHelperConnection;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.UUID;

import javax.inject.Inject;

public class PresenterPhotoCrop implements IPresenterPhotoCrop {

    private static long MAX_FILE_SIZE_BYTES = 4 * 1024 * 1024;

    @Inject
    IRepositoryPhotoUploadUri repositoryPhotoCrop;

    @Inject
    IRepositoryPhotoUpload repositoryPhotoUpload;

    @Inject
    ICachePhotoUpload cachePhotoUpload;

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

    private ListenerUpload listenerRequest;
    private WeakReference<IPresenterPhotoCropListener> refListener;

    public PresenterPhotoCrop() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryPhotoUpload.setListener(listenerRequest = new ListenerUpload());
    }

    @Override
    public void onCropCompleted(Uri file) {
        if (cachePhotoUpload.getFile().length() > MAX_FILE_SIZE_BYTES) {
            viewDialogs.showDialogMessage(R.string.error_photo_max_size);
            return;
        }

        cachePhotoUpload.setUri(file);
        cachePhotoUpload.setClientPhotoID(UUID.randomUUID().toString());

        cacheProfile.addPhotoLocal(cachePhotoUpload.getFileUri(), cachePhotoUpload.getClientPhotoId());

        repositoryPhotoCrop.request();
        onBackPressed();
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void setFile(File file) {
        cachePhotoUpload.setFile(file);
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

    private class ListenerUpload implements IRepositoryPhotoUploadListener {

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
