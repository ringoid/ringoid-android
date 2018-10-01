package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.response.ResponseSettings;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositorySettingsGet implements IRepositorySettingsGet {

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ApiRingoidProvider apiRingoid;


    private Call<ResponseSettings> request;
    private Callback<ResponseSettings> requestListener;


    public RepositorySettingsGet() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        request = apiRingoid.getAPI().settingsGet(cacheToken.getToken());

        request.enqueue(requestListener);
    }

    private class RequestListener implements Callback<ResponseSettings> {
        @Override
        public void onResponse(Call<ResponseSettings> call, Response<ResponseSettings> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheSettingsPrivacy.setData(
                        response.body().getWhoCanSeePhoto(),
                        response.body().getSafeDistanceInMeter(),
                        response.body().getPushLikes(),
                        response.body().isPushMessages(),
                        response.body().isPushMatches());

            }

        }

        @Override
        public void onFailure(Call<ResponseSettings> call, Throwable t) {

        }
    }
}
