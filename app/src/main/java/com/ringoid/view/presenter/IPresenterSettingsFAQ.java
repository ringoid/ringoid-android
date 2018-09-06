/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterSettingsFAQListener;

public interface IPresenterSettingsFAQ {

    void setListener(IPresenterSettingsFAQListener listener);

    void onCreateView();
}
