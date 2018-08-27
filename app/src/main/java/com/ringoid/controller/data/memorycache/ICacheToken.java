/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheToken {
    boolean isTokenExist();

    String getToken();

    void setToken(String token);

    void resetCache();
}
