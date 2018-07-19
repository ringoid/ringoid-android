package org.byters.ringoid.controller.data.repository;

import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterReferralConfirmListener;

public interface IRepositoryRegisterReferralConfirm {
    void request(String linkReferral);

    void setListener(IRepositoryRegisterReferralConfirmListener listener);
}
