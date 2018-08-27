/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.controller.data.repository.callback.IRepositoryRegisterCodeConfirmListener;

public interface IRepositoryRegisterCodeConfirm {
    void setListener(IRepositoryRegisterCodeConfirmListener listener);

    void request(String code);
}
