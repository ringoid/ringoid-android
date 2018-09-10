package com.ringoid;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.response.ResponseProfilePhotos;
import com.ringoid.controller.data.repository.IRepositoryProfilePhotos;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RepositoryProfilePhotos implements IRepositoryProfilePhotos {

    public static final String res_640 = "640x480";
    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheToken cacheToken;

    @Inject
    IApiRingoid apiRingoid;

    private Call<ResponseProfilePhotos> request;
    private Callback<ResponseProfilePhotos> requestListener;

    public RepositoryProfilePhotos() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        request = apiRingoid.profilePhotosGet(cacheToken.getToken(), res_640);

        request.enqueue(requestListener);
    }

    private class RequestListener implements Callback<ResponseProfilePhotos> {
        @Override
        public void onResponse(Call<ResponseProfilePhotos> call, Response<ResponseProfilePhotos> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isInvalidToken()) {

                //notifyTokenInvalid();
            }

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheProfile.setData(response.body().getPhotos());
            }

        }

        @Override
        public void onFailure(Call<ResponseProfilePhotos> call, Throwable t) {

        }
    }

}
