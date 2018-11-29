package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamRegisterCreateProfile {
    //private String accessToken;
    private int yearOfBirth;
    private String sex;
    private long dtTC;
    private long dtLA;
    private long dtPN;
    private String locale;
    private String deviceModel;
    private String osVersion;

    public RequestParamRegisterCreateProfile(int yearOfBirth, String sex, long dtTC, long dtLA, long dtPN, String locale, String deviceModel, String osVersion) {
        this.yearOfBirth = yearOfBirth;
        this.sex = sex;
        this.dtTC = dtTC;
        this.dtLA = dtLA;
        this.dtPN = dtPN;
        this.locale = locale;
        this.deviceModel = deviceModel;
        this.osVersion = osVersion;
    }
}
