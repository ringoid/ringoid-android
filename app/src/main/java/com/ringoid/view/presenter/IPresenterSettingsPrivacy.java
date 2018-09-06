/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;

public interface IPresenterSettingsPrivacy {

    void setListener(IPresenterSettingsPrivacyListener listener);

    void onCreateView();

    void onClickMessageFirstMatched();

    void onClickMessageFirstOnlyMe();
}
