package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.net.Uri;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.repository.IRepositoryPhotoUpload;
import com.ringoid.controller.data.repository.IRepositoryPhotoUploadUri;
import com.ringoid.controller.data.repository.callback.IRepositoryPhotoUploadListener;

import java.io.File;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterPhotoCrop implements IPresenterPhotoCrop {

    @Inject
    IRepositoryPhotoUploadUri repositoryPhotoCrop;

    @Inject
    IRepositoryPhotoUpload repositoryPhotoUpload;

    @Inject
    ICachePhotoUpload cachePhotoUpload;

    private ListenerUpload listenerRequest;
    private WeakReference<IPresenterPhotoCropListener> refListener;

    public PresenterPhotoCrop() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryPhotoUpload.setListener(listenerRequest = new ListenerUpload());
    }

    @Override
    public void onClickCrop(Uri file) {
        cachePhotoUpload.setUri(file);
        repositoryPhotoCrop.request();
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


    private class ListenerUpload implements IRepositoryPhotoUploadListener {

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
