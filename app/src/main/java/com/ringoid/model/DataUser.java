package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class DataUser implements Serializable {
    private String code;
    private String phone;

    public DataUser(String code, String phone) {
        this.code = code;
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }
}
