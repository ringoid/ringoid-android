package com.ringoid.controller.data.network.interceptor.listener;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IInterceptorRetryListener {
    void onRequestTokenInvalid();

    void onRequestErrorUnknown();

    void onRequestErrorAppVersion();

    void onRequestErrorConnection();

    void onRequestErrorWrongParams();
}
