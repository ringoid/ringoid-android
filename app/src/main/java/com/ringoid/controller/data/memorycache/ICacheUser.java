package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface ICacheUser {
    void setPhone(String code, String phone);

    String getPhoneCode();

    void resetCache();
}
