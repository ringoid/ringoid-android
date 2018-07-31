package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;

public interface IPresenterSettingsPrivacy {
    void onClickPrivacyPhotosAll();

    void onClickPrivacyPhotosLikes();

    void onClickPrivacyPhotosNoone();

    void setListener(IPresenterSettingsPrivacyListener listener);

    void onCreateView();
}
