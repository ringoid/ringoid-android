/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;

public interface IPresenterSettingsPrivacy {
    void onClickPrivacyPhotosAll();

    void onClickPrivacyPhotosLikes();

    void onClickPrivacyPhotosNoone();

    void setListener(IPresenterSettingsPrivacyListener listener);

    void onCreateView();
}
