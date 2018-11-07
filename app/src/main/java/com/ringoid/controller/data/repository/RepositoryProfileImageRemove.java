package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICachePhotoRemove;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.network.request.RequestParamProfileImageRemove;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.model.PhotoRemove;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryProfileImageRemove implements IRepositoryProfileImageRemove {

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICachePhotoRemove cachePhotoRemove;

    @Inject
    IRepositoryProfilePhotos repositoryProfile;

    public RepositoryProfileImageRemove() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request(String photoId, String originId) {

        String imageId = TextUtils.isEmpty(photoId) ? originId : photoId;

        apiRingoid.getAPI()
                .profileImageRemove(new RequestParamProfileImageRemove(cacheToken.getToken(), imageId))
                .enqueue(new ListenerRequest(photoId, originId));
    }

    private class ListenerRequest implements Callback<ResponseBase> {

        private String photoId, originId;

        ListenerRequest(String photoId, String originId) {
            this.photoId = photoId;
            this.originId = originId;
        }

        @Override
        public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
            if (!response.isSuccessful()) {
                cachePhotoRemove.add(photoId, originId);
                return;
            }

            cachePhotoRemove.remove(photoId, originId);
            repositoryProfile.request();

            if (!cachePhotoRemove.isDataExist())
                return;

            PhotoRemove item = cachePhotoRemove.getItemFirst();
            if (item == null) return;

            request(item.photoId, item.originId);
        }

        @Override
        public void onFailure(Call<ResponseBase> call, Throwable t) {
            cachePhotoRemove.add(photoId, originId);
        }
    }
}
