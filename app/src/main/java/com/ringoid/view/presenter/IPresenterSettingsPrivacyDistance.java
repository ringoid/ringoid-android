/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

public interface IPresenterSettingsPrivacyDistance {
    void onClickDistance(int resId);

    void onCreateView();

    void setListener(IPresenterSettingsPrivacyDistanceListener listener);
}
