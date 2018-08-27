/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network;

import com.ringoid.controller.data.network.request.RequestParamRegisterCodeConfirm;
import com.ringoid.controller.data.network.request.RequestParamRegisterPhone;
import com.ringoid.controller.data.network.request.RequestParamRegisterUserDetails;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.network.response.ResponseRegisterPhone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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

/*
    @GET("calendar")
    Call<ResponseCalendarPeriods> requestPeriods(@Query("device_id") String id, @Query("lang") String lang);
*/

}
