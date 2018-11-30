/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

import android.text.TextUtils;

public class ResponseBase {
    private String errorCode;
    private String errorMessage;

    public boolean isSuccess() {
        return TextUtils.isEmpty(errorCode);
    }

    public boolean isInvalidAccessTokenClientError() {
        return "InvalidAccessTokenClientError".equals(errorCode);
    }

    public boolean isInternalServerError() {
        return "InternalServerError".equals(errorCode);
    }

    public boolean isTooOldAppVersionClientError() {
        return "TooOldAppVersionClientError".equals(errorCode);
    }

    public boolean isWrongRequestParamsClientError() {
        return "WrongRequestParamsClientError".equals(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
