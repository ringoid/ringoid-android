package org.byters.ringoid.view;

import android.support.v4.app.FragmentManager;

public interface INavigator {
    void navigateFeed();

    void navigateLogin();

    void set(FragmentManager supportFragmentManager, int viewId);

    void navigateSettings();

    void navigateBlacklistPhones();

    void navigateChat();

    void navigateSettingsPrivacyDistance();

    void navigateSettingsPrivacy(boolean showPhotos);

    void navigateWebView(String url);
}
