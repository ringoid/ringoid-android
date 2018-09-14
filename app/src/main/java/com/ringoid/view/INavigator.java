/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public interface INavigator {
    void navigateFeed();

    void navigateLogin();

    void set(AppCompatActivity activity, FragmentManager supportFragmentManager, int viewId);

    void navigateSettings();

    void navigateBlacklistPhones();

    void navigatePhotoAdd();

    void navigateChat();

    void navigateSettingsPrivacyDistance();

    void navigateSettingsPrivacy(boolean showPhotos);

    void navigateWebView(String url, String subtitle);

    void navigateFeedback(Context context);

    void navigateEmailProtectionOfficer(Context context, String customerID);

    void navigateUrlExternal(Context context, String url);

    void navigateSettingsPush();

    void navigateBlacklistPhonesAdd();

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void navigateProfileUpdate();

    void navigateSettingsDataProtection();

    void statusbarShow();

    void statusbarHide();
}
