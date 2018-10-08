package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamRegisterPhone {

    private int countryCallingCode;
    private String phone;
    private long dtTC;
    private long dtLA;
    private long dtPN;
    private boolean clientValidationFail;
    private String locale;
    private String osVersion;
    private String deviceModel;

    public RequestParamRegisterPhone(int phoneCode, String s, long dateTerms, long dateAge, long datePrivacy, boolean b, String lang, String os, String device) {
        this.countryCallingCode = phoneCode;
        this.phone = s;
        this.dtTC = dateTerms;
        this.dtLA = dateAge;
        this.dtPN = datePrivacy;
        this.clientValidationFail = b;
        this.locale = lang;
        this.deviceModel = device;
        this.osVersion = os;
    }

}
