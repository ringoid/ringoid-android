package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

public interface IPresenterSettingsPrivacyDistance {
    void onClickDistance(int type);

    void onCreateView();

    void setListener(IPresenterSettingsPrivacyDistanceListener listener);
}
