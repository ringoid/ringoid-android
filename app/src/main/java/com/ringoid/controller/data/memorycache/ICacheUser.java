package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface ICacheUser {
    void setPhone(String code, String phone);

    int getPhoneCode();

    String getPhone();

    void resetCache();
}
