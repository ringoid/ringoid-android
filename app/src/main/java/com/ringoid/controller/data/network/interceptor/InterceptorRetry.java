package com.ringoid.controller.data.network.interceptor;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.network.interceptor.listener.IInterceptorRetryListener;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.view.presenter.util.IHelperConnection;
import com.ringoid.view.presenter.util.IHelperThreadMain;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InterceptorRetry implements Interceptor {

    @Inject
    Gson gson;

    @Inject
    IHelperThreadMain helperThreadMain;

    @Inject
    IHelperConnection helperConnection;

    private WeakReference<IInterceptorRetryListener> refListener;

    public InterceptorRetry() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request requestNew = request.newBuilder()
                .header("X-Ringoid-Android-BuildNum", String.valueOf(BuildConfig.VERSION_CODE))
                .method(request.method(), request.body())
                .build();

        if(BuildConfig.DEBUG){
            StringWriter sw = new StringWriter();
            new RuntimeException().printStackTrace(new PrintWriter(sw));
            Log.i("TESTER", sw.toString());
            if (request.url() != null) {
                Log.i("TESTER url", request.url().toString());
            }
            Log.i("TESTER method", request.method());
            if (request.body() != null) {
                Log.i("TESTER BODY", request.body().toString());
            }
        }

        Response response = null;

        for (int i = 0; i < 3; ++i) {
            response = chain.proceed(requestNew);

            if (!helperConnection.isConnectionExist()) {
                notifyErrorConnection();
                return getEmptyResponse(response);
            }

            if (!response.isSuccessful()) {
                notifyErrorUnknown();
                return getEmptyResponse(response);
            }

            ResponseBody body = response.body();
            if (body == null) {
                return getEmptyResponse(response);
            }

            String bodyString = getBody(body);
            if (!checkInternalServerError(bodyString)) {
                return response.newBuilder().body(ResponseBody.create(body.contentType(), bodyString)).build();
            }
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

        ResponseBase responseBase;
        try {
            responseBase = gson.fromJson(jsonData, ResponseBase.class);
        } catch (JsonSyntaxException e) {
            return false;
        }

        if (responseBase == null) return false;

        if (responseBase.isInvalidAccessTokenClientError()) {
            notifyErrorTokenInvalid();
            return false;
        }

        if (responseBase.isTooOldAppVersionClientError()) {
            notifyErrorAppVersion();
            return false;
        }

        if (responseBase.isWrongRequestParamsClientError()) {
            Crashlytics.logException(new RuntimeException(jsonData));
            notifyErrorWrongRequestParams();
            return false;
        }

        if (responseBase.isInternalServerError()) {
            Crashlytics.logException(new RuntimeException(jsonData));
            return true;
        }

        return false;
    }

    public void setListener(IInterceptorRetryListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifyErrorTokenInvalid() {
        helperThreadMain.post(new RunnableTokenInvalid());
    }

    private void notifyErrorUnknown() {
        helperThreadMain.post(new RunnableErrorUnknown());
    }


    private void notifyErrorConnection() {
        helperThreadMain.post(new RunnableErrorConnection());
    }


    private void notifyErrorAppVersion() {
        helperThreadMain.post(new RunnableErrorAppVersion());
    }

    private void notifyErrorWrongRequestParams() {
        helperThreadMain.post(new RunnableErrorWrongParams());
    }

    private class RunnableTokenInvalid implements Runnable {
        @Override
        public void run() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onRequestTokenInvalid();
        }
    }

    private class RunnableErrorUnknown implements Runnable {
        @Override
        public void run() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onRequestErrorUnknown();
        }
    }

    private class RunnableErrorConnection implements Runnable {
        @Override
        public void run() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onRequestErrorConnection();
        }
    }

    private class RunnableErrorAppVersion implements Runnable {
        @Override
        public void run() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onRequestErrorAppVersion();
        }
    }

    private class RunnableErrorWrongParams implements Runnable {
        @Override
        public void run() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onRequestErrorWrongParams();
        }
    }

}
