package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.presenter.callback.IPresenterSettingsPushListener;

public interface IPresenterSettingsPush {
    void setListener(IPresenterSettingsPushListener listener);

    void onCreateView();

    void updateChecked(int viewId);

    boolean isChecked(int viewId);

    void onSelectLikesType(int resId);
}
