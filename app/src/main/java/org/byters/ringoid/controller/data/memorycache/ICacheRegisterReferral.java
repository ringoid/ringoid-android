package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheRegisterReferralListener;

public interface ICacheRegisterReferral {
    void setListener(ICacheRegisterReferralListener listener);

    void setData(String title, String description);
}
