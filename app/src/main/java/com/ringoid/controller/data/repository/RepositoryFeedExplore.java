package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.response.ResponseNewFaces;
import com.ringoid.controller.data.repository.callback.IRepositoryNewPhotosListener;
import com.ringoid.model.DataProfile;
import com.ringoid.view.ui.util.ApiRingoidProvider;
import com.ringoid.view.ui.util.IScreenHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryFeedExplore implements IRepositoryFeedExplore {

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    IScreenHelper screenHelper;

    private Call<ResponseNewFaces> request;
    private Callback<ResponseNewFaces> requestListener;
    private WeakReference<IRepositoryNewPhotosListener> refListener;

    public RepositoryFeedExplore() {
        ApplicationRingoid.getComponent().inject(this);
        requestListener = new RepositoryFeedExplore.RequestListener();
    }

    @Override
    public void request() {
        if (request != null) request.cancel();

        request = apiRingoid.getAPI().getNewFacesGet(cacheToken.getToken(), screenHelper.getImageRatioString(), 10);

        request.enqueue(requestListener);

    }

    @Override
    public void setListener(IRepositoryNewPhotosListener listener) {
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

    private class RequestListener implements Callback<ResponseNewFaces> {
        @Override
        public void onResponse(Call<ResponseNewFaces> call, Response<ResponseNewFaces> response) {

            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {

                ResponseNewFaces responseNewFaces = (ResponseNewFaces) response.body();
                ArrayList<DataProfile> dataProfileArrayList = new ArrayList<>();

                for (ResponseNewFaces.Profiles dataProfiles:responseNewFaces.getProfiles()) {
                    DataProfile dataProfile = new DataProfile();
                    dataProfile.setUrls(dataProfiles.getPhotos());
                    dataProfileArrayList.add(dataProfile);
                }

                cacheExplore.setData(dataProfileArrayList);

                notifySuccess();
                return;
            }
            notifyError();

        }

        @Override
        public void onFailure(Call<ResponseNewFaces> call, Throwable t) {
            if (call.isCanceled()) return;
            notifyError();
        }
    }
}
