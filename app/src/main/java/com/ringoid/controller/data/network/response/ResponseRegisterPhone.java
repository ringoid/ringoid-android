package com.ringoid.controller.data.network.response;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public class ResponseRegisterPhone extends ResponseBase {
    private String sessionId;
    private String customerId;

    public String getSessionId() {
        return sessionId;
    }

    public boolean isErrorPhone() {
        return !isSuccess() && errorCode.equals("PhoneNumberClientError");
    }

    public boolean isErrorCode() {
        return !isSuccess() && errorCode.equals("CountryCallingCodeClientError");
    }

    public String getCustomerID() {
        return customerId;
    }
}
