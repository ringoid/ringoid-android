package com.ringoid.controller.data.repository.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IRepositoryRegisterUserDetailsListener {
    void onSuccess();

    void onErrorNoConnection();

    void onErrorUnknown();
}
