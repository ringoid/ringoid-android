package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.model.PhotoUpload;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPhotoUpload {

    private PhotoUpload photoUpload;

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

    public RepositoryPhotoUpload(PhotoUpload photoUpload) {
        this.photoUpload = photoUpload;
        ApplicationRingoid.getComponent().inject(this);
        listenerRequest = new ListenerRequest();
    }

    public void request() {
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), photoUpload.getFile());
        apiRingoid.getAPI().profilePhotoUpload(photoUpload.getUploadUri(), reqFile).enqueue(listenerRequest);
    }

    private class ListenerRequest implements Callback<Void> {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                cacheUser.setUserOld();
                cacheProfile.setPhotoLocalUploaded(photoUpload.getOriginPhotoId());
            } else onError();
        }


        private void onError() {
            cacheProfile.removeItemByLocalPhotoId(photoUpload.getClientPhotoId());
            viewDialog.showDialogMessage(R.string.error_photo_upload);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            onError();
        }
    }
}
