package com.ringoid;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.response.ResponseProfilePhotos;
import com.ringoid.controller.data.repository.IRepositoryProfilePhotos;
import com.ringoid.controller.data.repository.callback.IRepositoryProfilePhotosListener;
import com.ringoid.view.ui.util.ApiRingoidProvider;
import com.ringoid.view.ui.util.IScreenHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RepositoryProfilePhotos implements IRepositoryProfilePhotos {

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    IScreenHelper screenHelper;

    private Call<ResponseProfilePhotos> request;
    private Callback<ResponseProfilePhotos> requestListener;
    private WeakReference<IRepositoryProfilePhotosListener> refListener;

    public RepositoryProfilePhotos() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RequestListener();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        request = apiRingoid.getAPI().profilePhotosGet(cacheToken.getToken(), screenHelper.getImageRatioString());

        request.enqueue(requestListener);
    }

    @Override
    public void setListener(IRepositoryProfilePhotosListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void removeListener() {
        this.refListener = null;
    }

    private void notifyError() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onError();
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess();
    }

    private class RequestListener implements Callback<ResponseProfilePhotos> {
        @Override
        public void onResponse(Call<ResponseProfilePhotos> call, Response<ResponseProfilePhotos> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                cacheProfile.setData(response.body().getPhotos());
                notifySuccess();
                return;
            }
            notifyError();

        }

        @Override
        public void onFailure(Call<ResponseProfilePhotos> call, Throwable t) {
            if (call.isCanceled()) return;
            notifyError();
        }
    }

}
