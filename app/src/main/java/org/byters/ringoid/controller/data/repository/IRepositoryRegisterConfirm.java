package org.byters.ringoid.controller.data.repository;

import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;

public interface IRepositoryRegisterConfirm {
    void setListener(IRepositoryRegisterConfirmListener listener);

    void request(String textCheck);
}
