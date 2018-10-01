package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.request.RequestParamRegisterLogout;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRegisterLogout implements IRepositoryRegisterLogout {

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheToken cacheToken;

    private Call<ResponseBase> request;
    private Callback<ResponseBase> requestListener;

    public RepositoryRegisterLogout() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {

        if (request != null) request.cancel();

        request = apiRingoid.getAPI().registerLogout(new RequestParamRegisterLogout(
                cacheToken.getToken()));
        request.enqueue(requestListener);
    }


    private class RequestListener implements Callback<ResponseBase> {
        @Override
        public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {

        }

        @Override
        public void onFailure(Call<ResponseBase> call, Throwable t) {

        }
    }
}
