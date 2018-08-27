package com.ringoid.controller.data.network.request;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class RequestParamRegisterCodeConfirm {
    private String sessionId;
    private String verificationCode;

    public RequestParamRegisterCodeConfirm(String sessionId, String code) {
        this.sessionId = sessionId;
        this.verificationCode = code;
    }
}
