package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.presenter.callback.IPresenterDataProtectionListener;

public interface IPresenterDataProtection {
    void onCreateView();

    void setListener(IPresenterDataProtectionListener listener);

    void onClickCustomerId();
}
