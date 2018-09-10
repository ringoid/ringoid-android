package com.ringoid.controller.data.network.interceptor;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.view.presenter.util.ILogoutHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InterceptorRetry implements Interceptor {

    @Inject
    ILogoutHelper logoutHelper;

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

            if (!checkInternalServerError(body))
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

    private boolean checkInternalServerError(String jsonData) {
        if (TextUtils.isEmpty(jsonData)) return false;

        ResponseBase responseBase = gson.fromJson(jsonData, ResponseBase.class);

        if (responseBase == null) return false;

        if (responseBase.isInvalidToken()) {
            getLogoutHelper().logout();
            return false;
        }

        return responseBase.isInternalServerError();
    }

    private ILogoutHelper getLogoutHelper() {
        if (logoutHelper == null)
            ApplicationRingoid.getComponent().inject(this);
        return logoutHelper;
    }

}
