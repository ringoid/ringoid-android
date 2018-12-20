/*
 * Copyright (c) Ringoid Ltd, 2018. All Rights Reserved
 */

package com.ringoid.controller.data.network.response;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseNewFaces extends ResponseBase {

    @SerializedName("profiles")
    @Expose
    private List<Profiles> profiles;

    public List<Profiles> getProfiles() {
        return profiles;
    }

    public class Profiles implements Serializable {
        @SerializedName("userId")
        @Expose
        private String userId;

        @SerializedName("defaultSortingOrderPosition")
        @Expose
        private int defaultSortingOrderPosition;

        @SerializedName("photos")
        @Expose
        private List<Photos> photos;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getDefaultSortingOrderPosition() {
            return defaultSortingOrderPosition;
        }

        public void setDefaultSortingOrderPosition(int defaultSortingOrderPosition) {
            this.defaultSortingOrderPosition = defaultSortingOrderPosition;
        }

        public List<Photos> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photos> photos) {
            this.photos = photos;
        }
    }

    public class Photos implements Serializable {
        @SerializedName("photoId")
        @Expose
        private String photoId;

        @SerializedName("photoUri")
        @Expose
        private String photoUri;

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public String getPhotoUri() {
            return photoUri;
        }

        public void setPhotoUri(String photoUri) {
            this.photoUri = photoUri;
        }
    }
}
