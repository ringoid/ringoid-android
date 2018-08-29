/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

import android.text.TextUtils;

public class ResponseBase {
    private String errorCode;
    private String errorMessage;

    public boolean isSuccess() {
        return TextUtils.isEmpty(errorCode);
    }

    public boolean isInvalidToken() {
        return errorCode.equals("InvalidAccessTokenClientError");
    }
}
