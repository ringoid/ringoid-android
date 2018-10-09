/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

import android.text.TextUtils;

public class ResponseBase {
    String errorCode;
    private String errorMessage;

    public boolean isSuccess() {
        return TextUtils.isEmpty(errorCode);
    }

    public boolean isInvalidToken() {
        return errorCode.equals("InvalidAccessTokenClientError");
    }

    public boolean isInternalServerError() {
        return errorCode.equals("InternalServerError");
    }

    public boolean isErrorAppVersion() {
        return errorCode.equals("TooOldAppVersionClientError");
    }
}
