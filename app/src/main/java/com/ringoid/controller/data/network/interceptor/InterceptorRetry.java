package com.ringoid.controller.data.network.interceptor;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ringoid.controller.data.network.response.ResponseBase;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InterceptorRetry implements Interceptor {

    private Gson gson;

    public InterceptorRetry(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = null;

        for (int i = 0; i < 3; ++i) {
            response = chain.proceed(request);

            String body = getBody(response);

            if (!isInternalServerError(body))
                return response.newBuilder().body(ResponseBody.create(response.body().contentType(), body)).build();
        }

        return response;
    }

    private String getBody(Response response) {
        if (response == null || !response.isSuccessful()) return null;

        ResponseBody body = response.body();

        if (body == null) return null;

        String responseString = null;

        try {
            responseString = body.string();
        } catch (IOException e) {
        }

        return responseString;
    }

    private boolean isInternalServerError(String jsonData) {
        if (TextUtils.isEmpty(jsonData)) return false;

        ResponseBase responseBase = new Gson().fromJson(jsonData, ResponseBase.class);

        return responseBase != null && responseBase.isInternalServerError();
    }

}
