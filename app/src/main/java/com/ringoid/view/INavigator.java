/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ringoid.view.ui.fragment.FragmentBase;

public interface INavigator {
    void navigateFeed();

    void navigateLogin();

    void set(AppCompatActivity activity, FragmentManager supportFragmentManager, int viewId);

    void navigateBlacklistPhones();

    void navigatePhotoAdd();

    void navigateChat();

    void navigateSettingsPrivacyDistance();

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

    void navigateScreenDebug();

    void onResume(FragmentBase fragmentBase);

    void finish();

    void navigateErrorConnection();

    void navigateErrorAppversion();

    void navigateRegisterCodeConfirm();
}
