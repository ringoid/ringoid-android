/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterBlacklistPhonesListener;

public interface IPresenterBlacklistPhones {
    void onClickBlacklistAdd(String phone);

    void onViewCreated();

    void setListener(IPresenterBlacklistPhonesListener listener);
}
