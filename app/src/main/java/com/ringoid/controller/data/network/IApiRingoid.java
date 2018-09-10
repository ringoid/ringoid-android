/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network;

import com.ringoid.controller.data.network.request.RequestParamRegisterCodeConfirm;
import com.ringoid.controller.data.network.request.RequestParamRegisterLogout;
import com.ringoid.controller.data.network.request.RequestParamRegisterPhone;
import com.ringoid.controller.data.network.request.RequestParamRegisterUserDetails;
import com.ringoid.controller.data.network.request.RequestParamSettingsUpdate;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.controller.data.network.response.ResponseProfilePhotos;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.network.response.ResponseRegisterPhone;
import com.ringoid.controller.data.network.response.ResponseSettings;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiRingoid {

    @POST("/Prod/start_verification")
    Call<ResponseRegisterPhone> registerPhone(
            @Body RequestParamRegisterPhone param);

    @POST("/Prod/complete_verification")
    Call<ResponseRegisterCodeConfirm> registerCodeConfirm(
            @Body RequestParamRegisterCodeConfirm param);

    @POST("/Prod/create_profile")
    Call<ResponseBase> registerUserDetails(
            @Body RequestParamRegisterUserDetails param);

    @POST("/Prod/logout")
    Call<ResponseBase> registerLogout(
            @Body RequestParamRegisterLogout param);

    @POST("/Prod/update_settings")
    Call<ResponseBase> settingsUpdate(
            @Body RequestParamSettingsUpdate param);


    @GET("/Prod/get_settings")
    Call<ResponseSettings> settingsGet(@Query("accessToken") String token);

    @GET("https://a9o3cw1o7j.execute-api.eu-west-1.amazonaws.com/Prod/get_own_photos")
    Call<ResponseProfilePhotos> profilePhotosGet(@Query("accessToken") String token,
                                                 @Query("resolution") String res);

}
