package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

public interface IPresenterSettingsPrivacyDistance {
    void onClickDistance(int type);

    void onCreateView();

    void setListener(IPresenterSettingsPrivacyDistanceListener listener);
}
