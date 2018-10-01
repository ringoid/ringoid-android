package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.google.gson.Gson;
import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.network.IApiRingoid;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRingoidProvider {

    @Inject
    OkHttpProvider okHttpProvider;

    @Inject
    Gson gson;

    private IApiRingoid api;

    public ApiRingoidProvider() {
        ApplicationRingoid.getComponent().inject(this);

        api = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpProvider.getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(IApiRingoid.class);
    }

    public IApiRingoid getAPI() {
        return api;
    }
}
