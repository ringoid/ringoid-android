package com.ringoid.controller.data.repository;

import com.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;

public interface IRepositoryRegisterConfirm {
    void setListener(IRepositoryRegisterConfirmListener listener);

    void request(String textCheck);
}
