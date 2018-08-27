package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamRegisterUserDetails {
    private String accessToken;
    private int yearOfBirth;
    private String sex;

    public RequestParamRegisterUserDetails(String token, int year, String sex) {
        this.accessToken = token;
        this.yearOfBirth = year;
        this.sex = sex;
    }
}
