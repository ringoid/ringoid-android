/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public interface INavigator {
    void navigateFeed();

    void navigateLogin();

    void set(FragmentManager supportFragmentManager, int viewId);

    void navigateSettings();

    void navigateBlacklistPhones();

    void navigatePhotoAdd(AppCompatActivity activity);

    void navigateChat();

    void navigateSettingsPrivacyDistance();

    void navigateSettingsPrivacy(boolean showPhotos);

    void navigateWebView(String url);

    void navigateFeedback(Context context, int versionCode, String versionName, String sdkInt, String model);

    void navigateSettingsPush();

    void navigateWelcome(boolean isLogin);

    void navigateBlacklistPhonesAdd();

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
