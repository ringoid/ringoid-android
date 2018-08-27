package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class DataUser implements Serializable {
    private String code;
    private String phone;
    private boolean registered;

    public DataUser(String code, String phone) {
        this.code = code;
        this.phone = phone;
        registered = false;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
