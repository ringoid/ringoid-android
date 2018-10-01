package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.network.interceptor.InterceptorRetry;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

public class OkHttpProvider {


    @Inject
    InterceptorRetry interceptor;

    private OkHttpClient client;

    public OkHttpProvider() {
        ApplicationRingoid.getComponent().inject(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor);

        client = builder.build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
