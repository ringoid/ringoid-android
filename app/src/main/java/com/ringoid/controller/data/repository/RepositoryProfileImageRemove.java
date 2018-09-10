package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.request.RequestParamProfileImageRemove;
import com.ringoid.controller.data.network.response.ResponseBase;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryProfileImageRemove implements IRepositoryProfileImageRemove {

    @Inject
    IApiRingoid apiRingoid;

    @Inject
    ICacheToken cacheToken;

    private ListenerRequest listenerRequest;
    private Call<ResponseBase> request;

    public RepositoryProfileImageRemove() {
        ApplicationRingoid.getComponent().inject(this);
        listenerRequest = new ListenerRequest();
    }

    @Override
    public void request(String imageId) {
        if (request != null) request.cancel();
        request = apiRingoid.profileImageRemove(new RequestParamProfileImageRemove(cacheToken.getToken(), imageId));
        request.enqueue(listenerRequest);
    }

    private class ListenerRequest implements Callback<ResponseBase> {
        @Override
        public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {

        }

        @Override
        public void onFailure(Call<ResponseBase> call, Throwable t) {

        }
    }
}
