/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository.callback;

public interface IRepositoryRegisterCodeConfirmListener {
    void onSuccess();

    void onErrorNoPendingClient();

    void onErrorInvalidCode();

    void onErrorUnknown();
}
