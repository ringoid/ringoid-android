package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.request.RequestParamSettingsUpdate;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositorySettingsSave implements IRepositorySettingsSave {

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheToken cacheToken;

    private Call<ResponseBase> request;
    private Callback<ResponseBase> requestListener;

    public RepositorySettingsSave() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        request = apiRingoid.getAPI().settingsUpdate(new RequestParamSettingsUpdate(
                cacheToken.getToken(),
                cacheSettingsPrivacy.getWhoCanSeePhotosString(),
                cacheSettingsPrivacy.getDistance(),
                cacheSettingsPrivacy.isCheckedPushMessages(),
                cacheSettingsPrivacy.isCheckedPushMatches(),
                cacheSettingsPrivacy.getPushLikes()));

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
