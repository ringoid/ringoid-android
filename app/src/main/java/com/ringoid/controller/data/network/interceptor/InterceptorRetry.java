package com.ringoid.controller.data.network.interceptor;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.network.interceptor.listener.IInterceptorRetryListener;
import com.ringoid.controller.data.network.response.ResponseBase;

import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InterceptorRetry implements Interceptor {

    @Inject
    Gson gson;

    private WeakReference<IInterceptorRetryListener> refListener;

    public InterceptorRetry() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = null;

        for (int i = 0; i < 3; ++i) {
            response = chain.proceed(request);

            if (!response.isSuccessful()) {
                notifyErrorUnknown();
                return getEmptyResponse(response);
            }

            ResponseBody body = response.body();

            if (body == null) return getEmptyResponse(response);

            String bodyString = getBody(body);

            if (!checkInternalServerError(bodyString))
                return response.newBuilder().body(ResponseBody.create(body.contentType(), bodyString)).build();
        }

        return response;
    }

    private Response getEmptyResponse(Response response) {
        return response.newBuilder().build();
    }

    private String getBody(ResponseBody body) {
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
            notifyErrorTokenInvalid();
            return false;
        }

        return responseBase.isInternalServerError();
    }

    public void setListener(IInterceptorRetryListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifyErrorTokenInvalid() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onRequestTokenInvalid();
    }

    private void notifyErrorUnknown() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onRequestErrorUnknown();
    }

}
