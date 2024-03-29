/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

public class ResponseRegisterCodeConfirm extends ResponseBase {
    private String accessToken;
    private boolean accountAlreadyExist;

    public String getToken() {
        return accessToken;
    }

    public boolean isRegistered() {
        return accountAlreadyExist;
    }

    public boolean isNoPendingClient() {
        return !isSuccess() && "NoPendingVerificationClientError".equals(getErrorCode());
    }

    public boolean isInavlidVerificationCode() {
        return !isSuccess() && "WrongVerificationCodeClientError".equals(getErrorCode());
    }
}
