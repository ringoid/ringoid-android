/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network;

import com.ringoid.controller.data.network.request.RequestParamProfileImageRemove;
import com.ringoid.controller.data.network.request.RequestParamRegisterCodeConfirm;
import com.ringoid.controller.data.network.request.RequestParamRegisterLogout;
import com.ringoid.controller.data.network.request.RequestParamRegisterPhone;
import com.ringoid.controller.data.network.request.RequestParamCreateProfile;
import com.ringoid.controller.data.network.request.RequestParamSettingsUpdate;
import com.ringoid.controller.data.network.request.RequestPhotoUploadUri;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.controller.data.network.response.ResponseProfilePhotoUri;
import com.ringoid.controller.data.network.response.ResponseProfilePhotos;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.network.response.ResponseRegisterPhone;
import com.ringoid.controller.data.network.response.ResponseCreateProfile;
import com.ringoid.controller.data.network.response.ResponseSettings;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IApiRingoid {

    @POST("/auth/create_profile")
    Call<ResponseCreateProfile> createProfile(
            @Body RequestParamCreateProfile param);

    @POST("/image/get_presigned")
    Call<ResponseProfilePhotoUri> profilePhotoUri(@Body RequestPhotoUploadUri param);

    @PUT
    Call<Void> profilePhotoUpload(@Url String url,
                                  @Body RequestBody image);

    @GET("/image/get_own_photos")
    Call<ResponseProfilePhotos> profilePhotosGet(@Query("accessToken") String token,
                                                 @Query("resolution") String res);

    @POST("/image/delete_photo")
    Call<ResponseBase> profileImageRemove(@Body RequestParamProfileImageRemove param);

    //-----------------------------------------------------------------
    //DEPRECATED
    @POST("/Prod/start_verification")
    Call<ResponseRegisterPhone> registerPhone(
            @Body RequestParamRegisterPhone param);

    @POST("/Prod/complete_verification")
    Call<ResponseRegisterCodeConfirm> registerCodeConfirm(
            @Body RequestParamRegisterCodeConfirm param);

    @POST("/Prod/logout")
    Call<ResponseBase> registerLogout(
            @Body RequestParamRegisterLogout param);

    @POST("/Prod/update_settings")
    Call<ResponseBase> settingsUpdate(
            @Body RequestParamSettingsUpdate param);


    @GET("/Prod/get_settings")
    Call<ResponseSettings> settingsGet(@Query("accessToken") String token);


    @POST("https://xvlq70j9zb.execute-api.eu-west-1.amazonaws.com/Prod/timeout")
    Call<ResponseBase> testTimeout();

    @POST("https://xvlq70j9zb.execute-api.eu-west-1.amazonaws.com/Prod/invalidtoken")
    Call<ResponseBase> testTokenInvalid();

    @POST("https://xvlq70j9zb.execute-api.eu-west-1.amazonaws.com/Prod/nonok")
    Call<ResponseBase> testResponseNot200();

    @POST("https://xvlq70j9zb.execute-api.eu-west-1.amazonaws.com/Prod/old_version")
    Call<ResponseBase> testAppVersion();

    @POST("https://xvlq70j9zb.execute-api.eu-west-1.amazonaws.com/Prod/internalerror")
    Call<ResponseBase> testInternalServerError();

    @POST("https://lhksdfgkjsgfhjsdgfsdgjsdfgjsdlfksd.com")
    Call<ResponseBase> testUnknownHost();

}
