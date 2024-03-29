package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.repository.callback.IRepositoryErrorUnknownListener;
import com.ringoid.view.presenter.util.IHelperThreadMain;
import com.ringoid.view.ui.util.OkHttpProvider;

import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class RepositoryErrorUnknown implements IRepositoryErrorUnknown {

    @Inject
    OkHttpProvider okHttpClient;

    @Inject
    IHelperThreadMain helperThreadMain;

    private ListenerRequest listenerRequest;
    private WeakReference<IRepositoryErrorUnknownListener> refListener;

    public RepositoryErrorUnknown() {
        ApplicationRingoid.getComponent().inject(this);
        listenerRequest = new ListenerRequest();
    }

    @Override
    public void request(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.getClient().newCall(request).enqueue(listenerRequest);
    }

    @Override
    public void setListener(IRepositoryErrorUnknownListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private void notifySuccess(String message) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess(message);

    }

    private class ListenerRequest implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()
                    && response.body() != null)
                helperThreadMain.post(new RunnableResponse(response.body().string()));

        }
    }

    private class RunnableResponse implements Runnable {
        private String message;

        public RunnableResponse(String string) {
            this.message = string;
        }

        @Override
        public void run() {
            notifySuccess(message);
        }
    }
}
