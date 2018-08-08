package org.byters.ringoid.view;

import android.content.Context;
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

    void navigateFeedback(Context context, int versionCode, String versionName, String sdkInt, String model);

    void navigateSettingsPush();

    void navigateWelcome(boolean isLogin);

    void navigateBlacklistPhonesAdd();

    boolean onBackPressed();
}
