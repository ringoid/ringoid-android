package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.AsyncTask;
import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.request.RequestPhotoUploadUri;
import com.ringoid.controller.data.network.response.ResponseProfilePhotoUri;
import com.ringoid.model.PhotoUpload;
import com.ringoid.view.ui.util.ApiRingoidProvider;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RepositoryPhotoUploadSync implements IRepositoryPhotoUploadSync {

    private static final String EXT_JPG = "jpg";

    @Inject
    ICachePhotoUpload cachePhotoUpload;

    @Inject
    ApiRingoidProvider apiRingoid;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheUser cacheUser;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private AsyncTask<Void, String, Void> request;


    public RepositoryPhotoUploadSync() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request() {
        if (request != null) return;
        request = new AsyncRequest();
        request.execute();
    }

    @Override
    public void cancel(String originId) {
        if (request == null) return;
        request.cancel(true);
        request = null;
        request();
    }

    private class AsyncRequest extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            while (cachePhotoUpload.isDataExist()) {

                PhotoUpload item = cachePhotoUpload.getItemFirst();

                ResponseProfilePhotoUri responseUri;

                try {
                    responseUri = apiRingoid.getAPI()
                            .profilePhotoUri(new RequestPhotoUploadUri(cacheToken.getToken(), item.getClientPhotoId(), EXT_JPG))
                            .execute().body();

                    if (responseUri == null || !responseUri.isSuccess())
                        return null;

                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), item.getFile());

                    apiRingoid.getAPI()
                            .profilePhotoUpload(responseUri.getUri(), reqFile)
                            .execute();

                    cachePhotoUpload.removeItem(responseUri.getClientPhotoId());

                } catch (IOException e) {
                    return null;
                }

                publishProgress(responseUri.getClientPhotoId(), responseUri.getOriginPhotoId());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... params) {
            super.onProgressUpdate(params);

            if (params == null || params.length != 2) return;

            String photoClientId = params[0];
            String photoOriginId = params[1];

            if (TextUtils.isEmpty(photoClientId)
                    || TextUtils.isEmpty(photoOriginId))
                return;
            cacheProfile.updateLocalPhoto(photoClientId, photoOriginId);
            cacheInterfaceState.updatePhotoSelected(photoClientId, photoOriginId);
            cacheUser.setUserOld();
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            request = null;
        }
    }
}
