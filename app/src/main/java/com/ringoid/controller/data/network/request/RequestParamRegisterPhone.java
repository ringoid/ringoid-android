package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamRegisterPhone {

    private int countryCallingCode;
    private String phone;
    private long dtTC;
    private long dtLA;
    private long dtPN;
    private boolean clientValidationFail;
    private String local;

    public RequestParamRegisterPhone(int phoneCode, String s, long dateTerms, long dateAge, long datePrivacy, boolean b, String lang) {
        this.countryCallingCode = phoneCode;
        this.phone = s;
        this.dtTC = dateTerms;
        this.dtLA = dateAge;
        this.dtPN = datePrivacy;
        this.clientValidationFail = b;
        this.local = lang;
    }

}