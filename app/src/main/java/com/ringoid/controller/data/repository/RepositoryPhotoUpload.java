package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.repository.callback.IRepositoryPhotoUploadListener;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPhotoUpload implements IRepositoryPhotoUpload {

    @Inject
    ICachePhotoUpload cachePhotoUpload;

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    IRepositoryProfilePhotos repositoryProfilePhotos;

    @Inject
    ICacheUser cacheUser;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IViewDialogs viewDialog;

    private ListenerRequest listenerRequest;
    private Call<Void> request;
    private WeakReference<IRepositoryPhotoUploadListener> refListener;

    public RepositoryPhotoUpload() {
        ApplicationRingoid.getComponent().inject(this);
        listenerRequest = new ListenerRequest();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        if (!cachePhotoUpload.isDataExist()) return;

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), cachePhotoUpload.getFile());

        request = apiRingoid.getAPI().profilePhotoUpload(cachePhotoUpload.getUploadUri(), reqFile);
        request.enqueue(listenerRequest);
    }

    @Override
    public void setListener(IRepositoryPhotoUploadListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifyError() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onError();
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private class ListenerRequest implements Callback<Void> {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                cacheUser.setUserOld();
                cacheProfile.setPhotoLocalUploaded(cachePhotoUpload.getOriginPhotoId());
                notifySuccess();
            } else onError();
        }


        private void onError() {
            cacheProfile.removeItemByLocalPhotoId(cachePhotoUpload.getClientPhotoId());
            viewDialog.showDialogMessage(R.string.error_photo_upload);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            if (call.isCanceled()) return;
            onError();
            notifyError();
        }
    }
}
