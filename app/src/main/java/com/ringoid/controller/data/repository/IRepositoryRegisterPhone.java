/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.repository;

import com.ringoid.controller.data.repository.callback.IRepositoryRegisterPhoneListener;

public interface IRepositoryRegisterPhone {
    void request();

    void setListener(IRepositoryRegisterPhoneListener listener);
}
