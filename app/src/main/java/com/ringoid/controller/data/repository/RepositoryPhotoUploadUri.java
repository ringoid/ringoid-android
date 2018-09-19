package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.request.RequestPhotoUploadUri;
import com.ringoid.controller.data.network.response.ResponseProfilePhotoUri;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPhotoUploadUri implements IRepositoryPhotoUploadUri {

    private static final String EXT_JPG = "jpg";

    @Inject
    IApiRingoid apiRingoid;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICachePhotoUpload cachePhotoUpload;

    @Inject
    IRepositoryPhotoUpload repositoryPhotoUpload;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ICacheProfile cacheProfile;

    private Callback<ResponseProfilePhotoUri> requestUriListener;
    private Call<ResponseProfilePhotoUri> request;

    public RepositoryPhotoUploadUri() {
        ApplicationRingoid.getComponent().inject(this);
        requestUriListener = new ListenerRequestUri();
    }

    @Override
    public void request() {
        if (request != null)
            request.cancel();

        request = apiRingoid.profilePhotoUri(new RequestPhotoUploadUri(cacheToken.getToken(), cachePhotoUpload.getClientPhotoId(), EXT_JPG));
        request.enqueue(requestUriListener);

    }

    private class ListenerRequestUri implements Callback<ResponseProfilePhotoUri> {
        @Override
        public void onResponse(Call<ResponseProfilePhotoUri> call, Response<ResponseProfilePhotoUri> response) {

            if (response.isSuccessful()
                    && response.body().isSuccess()) {

                cachePhotoUpload.setUploadUri(response.body().getUri());
                cachePhotoUpload.setOriginPhotoId(response.body().getClientPhotoId(), response.body().getOriginPhotoId());
                cacheInterfaceState.setProfileOriginPhotoId(response.body().getOriginPhotoId());
                cacheProfile.updateLocalPhoto(response.body().getClientPhotoId(), response.body().getOriginPhotoId());

                repositoryPhotoUpload.request();
            }

        }

        @Override
        public void onFailure(Call<ResponseProfilePhotoUri> call, Throwable t) {

        }
    }
}
