/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

import java.io.Serializable;

public class DataBlacklistPhone implements Serializable {
    private String phone;
    private String code;

    public DataBlacklistPhone(String code, String phone) {
        this.phone = phone;
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isPhoneEquals(String code, String phone) {
        return phone.equals(this.phone) && code.equals(this.code);
    }

    public String getCode() {
        return code;
    }
}
