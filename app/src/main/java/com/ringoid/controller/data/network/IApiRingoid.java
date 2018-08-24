/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network;

import com.ringoid.controller.data.network.response.ResponseRegisterCodeConfirm;
import com.ringoid.controller.data.network.response.ResponseRegisterPhone;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IApiRingoid {

    @POST("/Prod/start_verification")
    @FormUrlEncoded
    Call<ResponseRegisterPhone> registerPhone(
            @Field("countryCallingCode") int code,
            @Field("phone") String phone,
            @Field("dtTC") long dateTerms,
            @Field("dtLA") long dateAge,
            @Field("dtPN") long datePrivacy,
            @Field("clientValidationFail") boolean isPhoneInvalid,
            @Field("locale") String locale);

    @POST("/Prod/complete_verification")
    @FormUrlEncoded
    Call<ResponseRegisterCodeConfirm> registerCodeConfirm(
            @Field("SessionId") String id,
            @Field("VerificationCode") int code);

/*
    @GET("calendar")
    Call<ResponseCalendarPeriods> requestPeriods(@Query("device_id") String id, @Query("lang") String lang);
*/

}
