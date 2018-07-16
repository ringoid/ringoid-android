package org.byters.ringoid.controller.data.repository;

import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterListener;

public interface IRepositoryRegister {
    void request(String phone);

    void setListener(IRepositoryRegisterListener listener);
}
