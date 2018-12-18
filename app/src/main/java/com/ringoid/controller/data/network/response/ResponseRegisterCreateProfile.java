/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

import android.text.TextUtils;

@Deprecated
public class ResponseRegisterCreateProfile extends ResponseBase{
    String accessToken;
    String customerId;
    String errorCode;
    String errorMessage;

    public boolean isSuccess() {
        return TextUtils.isEmpty(errorCode);
    }

    public boolean isWrongYearOfBirthClientError() {
        return !isSuccess() && errorCode.equals("WrongYearOfBirthClientError");
    }

    public boolean isWrongSexClientError() {
        return !isSuccess() && errorCode.equals("WrongSexClientError");
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
