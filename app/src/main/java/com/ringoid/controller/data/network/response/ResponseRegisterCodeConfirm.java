package com.ringoid.controller.data.network.response;

public class ResponseRegisterCodeConfirm extends ResponseBase {
    private String token;
    private int registered;

    public String getToken() {
        return token;
    }

    public boolean isRegistered() {
        return registered == 1;
    }
}
