/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.network.response;

public class ResponseCreateProfile extends ResponseBase {
    String accessToken;
    String customerId;

    public String getAccessToken() {
        return accessToken;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean isWrongYearOfBirthClientError() {
        return "WrongYearOfBirthClientError".equals(getErrorCode());
    }

    public boolean isWrongSexClientError() {
        return "WrongSexClientError".equals(getErrorCode());
    }

}

