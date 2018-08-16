/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

package com.ringoid.controller.data.memorycache;

public class CacheUser implements ICacheUser {
    private String code;
    private String phone;

    @Override
    public void setPhone(String code, String phone) {
        this.code = code;
        this.phone = phone;
    }

    @Override
    public String getPhoneCode() {
        return code;
    }
}
