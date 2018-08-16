/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.controller.data.repository.callback.IRepositoryRegisterListener;

public interface IRepositoryRegister {
    void request(String phone);

    void setListener(IRepositoryRegisterListener listener);
}
