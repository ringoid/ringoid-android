package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.request.RequestPhotoUploadUri;
import com.ringoid.controller.data.network.response.ResponseProfilePhotoUri;
import com.ringoid.model.PhotoUpload;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPhotoUploadUri {

    private static final String EXT_JPG = "jpg";

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    IViewDialogs viewDialogs;

    private PhotoUpload photoUpload;
    private Callback<ResponseProfilePhotoUri> requestUriListener;

    public RepositoryPhotoUploadUri(PhotoUpload photoUpload) {
        this.photoUpload = photoUpload;
        ApplicationRingoid.getComponent().inject(this);
        requestUriListener = new ListenerRequestUri();
    }

    public void request() {
        if (photoUpload == null) return;
        apiRingoid.getAPI().profilePhotoUri(new RequestPhotoUploadUri(cacheToken.getToken(), photoUpload.getClientPhotoId(), EXT_JPG)).enqueue(requestUriListener);
    }

    private class ListenerRequestUri implements Callback<ResponseProfilePhotoUri> {
        @Override
        public void onResponse(Call<ResponseProfilePhotoUri> call, Response<ResponseProfilePhotoUri> response) {

            if (response.isSuccessful()
                    && response.body().isSuccess()) {

                photoUpload.setUploadUri(response.body().getUri());
                photoUpload.setOriginPhotoId(response.body().getClientPhotoId(), response.body().getOriginPhotoId());
                cacheInterfaceState.setProfileOriginPhotoId(response.body().getOriginPhotoId());
                cacheProfile.updateLocalPhoto(response.body().getClientPhotoId(), response.body().getOriginPhotoId());

                new RepositoryPhotoUpload(photoUpload).request();
            } else onError();

        }

        @Override
        public void onFailure(Call<ResponseProfilePhotoUri> call, Throwable t) {
            onError();
        }

        private void onError() {
            cacheProfile.removeItemByLocalPhotoId(photoUpload.getClientPhotoId());
            viewDialogs.showDialogMessage(R.string.error_photo_upload);
        }
    }
}
