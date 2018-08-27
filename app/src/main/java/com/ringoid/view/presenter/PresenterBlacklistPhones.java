/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheBlacklist;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.view.presenter.callback.IPresenterBlacklistPhonesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterBlacklistPhones implements IPresenterBlacklistPhones {

    @Inject
    ICacheBlacklist cacheBlacklist;

    @Inject
    ICacheUser cacheUser;
    private WeakReference<IPresenterBlacklistPhonesListener> refListener;

    public PresenterBlacklistPhones() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClickBlacklistAdd(String code, String phone) {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) return;
        cacheBlacklist.addPhone(code, phone);
    }

    @Override
    public void onViewCreated() {
        int code = cacheUser.getPhoneCode();
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPhoneData(code);
    }

    @Override
    public void setListener(IPresenterBlacklistPhonesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
