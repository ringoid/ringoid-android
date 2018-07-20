package org.byters.ringoid.view.presenter;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheBlacklist;

import javax.inject.Inject;

public class PresenterBlacklistPhones implements IPresenterBlacklistPhones {

    @Inject
    ICacheBlacklist cacheBlacklist;

    public PresenterBlacklistPhones() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClickBlacklistAdd(String phone) {
        if (TextUtils.isEmpty(phone)) return;
        cacheBlacklist.addPhone(phone);
    }
}
