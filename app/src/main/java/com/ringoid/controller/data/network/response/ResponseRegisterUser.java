/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

import android.text.TextUtils;

public class ResponseRegisterUser {
    String accessToken;
    String customerId;
    String errorCode;
    String errorMessage;

    public boolean isSuccess() {
        return TextUtils.isEmpty(errorCode);
    }

    public boolean isInvalidToken() {
        return !isSuccess() && errorCode.equals("InvalidAccessTokenClientError");
    }

    public boolean isInternalServerError() {
        return !isSuccess() && errorCode.equals("InternalServerError");
    }

    public boolean isErrorAppVersion() {
        return !isSuccess() && errorCode.equals("TooOldAppVersionClientError");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
