package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.presenter.callback.IPresenterSettingsListener;

public interface IPresenterSettings {
    void onCreateView();

    void setListener(IPresenterSettingsListener listener);

    void onScroll(int scrollY);

    void onConfirmLogout();
}
