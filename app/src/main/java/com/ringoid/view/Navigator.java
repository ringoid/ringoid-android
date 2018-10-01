/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.R;
import com.ringoid.view.ui.fragment.FragmentBase;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhonesAdd;
import com.ringoid.view.ui.fragment.FragmentChat;
import com.ringoid.view.ui.fragment.FragmentDataProtection;
import com.ringoid.view.ui.fragment.FragmentLogin;
import com.ringoid.view.ui.fragment.FragmentPages;
import com.ringoid.view.ui.fragment.FragmentPhotoCrop;
import com.ringoid.view.ui.fragment.FragmentSettings;
import com.ringoid.view.ui.fragment.FragmentSettingsDebug;
import com.ringoid.view.ui.fragment.FragmentSettingsPrivacyDistance;
import com.ringoid.view.ui.fragment.FragmentSettingsPush;
import com.ringoid.view.ui.fragment.FragmentWebView;
import com.ringoid.view.ui.util.IHelperFullscreen;
import com.ringoid.view.ui.util.IHelperScreenshots;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class Navigator implements INavigator {

    private static final String CURRENT_FRAGMENT_PAGE = "current_fragment";
    private static final int SELECT_FILE = 173;

    @Inject
    IHelperFullscreen helperFullscreen;

    @Inject
    IHelperScreenshots helperScreenshots;

    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;
    private WeakReference<AppCompatActivity> refActivity;

    public Navigator() {
        ApplicationRingoid.getComponent().inject(this);
    }

    private void onPageShow(PAGE_ENUM page) {

        if (page == PAGE_ENUM.CHAT)
            helperScreenshots.disableScreenshots();
        else
            helperScreenshots.enableScreenshots();

        if (page == PAGE_ENUM.FEED) {
            clearBackStack();
            helperFullscreen.statusbarShowFullscreen();
        }

        if (page == PAGE_ENUM.LOGIN
                || page == PAGE_ENUM.PROFILE_UPDATE) {
            clearBackStack();
            helperFullscreen.statusbarShowResizeable();
        }

        if (page == PAGE_ENUM.WEBVIEW_EXTERNAL
                || page == PAGE_ENUM.SETTINGS_PUSH
                || page == PAGE_ENUM.EMAIL_OFFICER
                || page == PAGE_ENUM.FEEDBACK
                || page == PAGE_ENUM.WEBVIEW
                || page == PAGE_ENUM.SETTINGS_PRIVACY_DISTANCE
                || page == PAGE_ENUM.CHAT
                || page == PAGE_ENUM.PHOTO_ADD
                || page == PAGE_ENUM.PHOTO_CROP
                || page == PAGE_ENUM.SETTINGS_DEBUG
                || page == PAGE_ENUM.SETTINGS_DATA_PROTECTION
                || page == PAGE_ENUM.SETTINGS
                || page == PAGE_ENUM.BLACKLIST_PHONES
                || page == PAGE_ENUM.BLACKLIST_PHONES_ADD)
            if (refActivity != null && refActivity.get() != null)
                helperFullscreen.statusbarShowResizeable();
    }

    @Override
    public void navigateFeed() {
        onPageShow(PAGE_ENUM.FEED);

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentPages())
                .commit();
    }

    @Override
    public void navigateLogin() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.LOGIN);

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLogin(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    private void clearBackStack() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        FragmentManager fm = refFragmentManager.get();

        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void set(AppCompatActivity activity, FragmentManager supportFragmentManager, int viewId) {
        this.refFragmentManager = new WeakReference<>(supportFragmentManager);
        this.refActivity = new WeakReference<>(activity);
        this.viewId = viewId;
    }

    @Override
    public void navigateSettings() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.SETTINGS);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettings(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateBlacklistPhones() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.BLACKLIST_PHONES);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentBlacklistPhones(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateBlacklistPhonesAdd() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.BLACKLIST_PHONES_ADD);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentBlacklistPhonesAdd(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_PAGE);
        if (fragment == null) return false;
        return ((FragmentBase) fragment).onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_FILE && data != null) {
            navigatePhotoCrop(data.getData());
        }
    }

    @Override
    public void navigateProfileUpdate() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        onPageShow(PAGE_ENUM.PROFILE_UPDATE);
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentLogin.getInstanceProfileUpdate(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateSettingsDataProtection() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.SETTINGS_DATA_PROTECTION);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentDataProtection(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateScreenDebug() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.SETTINGS_DEBUG);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsDebug(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }


    private void navigatePhotoCrop(Uri data) {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.PHOTO_CROP);
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentPhotoCrop.getInstance(data), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigatePhotoAdd() {
        if (refActivity == null || refActivity.get() == null) return;
        onPageShow(PAGE_ENUM.PHOTO_ADD);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (intent.resolveActivity(refActivity.get().getPackageManager()) == null) return;

        refActivity.get().startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void navigateChat() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.CHAT);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentChat(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateSettingsPrivacyDistance() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.SETTINGS_PRIVACY_DISTANCE);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsPrivacyDistance(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateWebView(String url, String subtitle) {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        onPageShow(PAGE_ENUM.WEBVIEW);
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentWebView.getInstance(url, subtitle), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateFeedback(Context context) {
        onPageShow(PAGE_ENUM.FEEDBACK);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@ringoid.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getEmailSubject());
        intent.putExtra(Intent.EXTRA_TEXT, "");

        if (intent.resolveActivity(context.getPackageManager()) == null) return;

        context.startActivity(intent);
    }

    private String getEmailSubject() {
        return String.format("Ringoid Android App %d(%s) %s %s %s, %s, %s",
                BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME,
                Build.MODEL,
                Build.MANUFACTURER,
                Build.PRODUCT,
                Build.VERSION.RELEASE,
                Build.VERSION.SDK_INT);
    }

    @Override
    public void navigateEmailProtectionOfficer(Context context, String customerId) {
        onPageShow(PAGE_ENUM.EMAIL_OFFICER);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:data.protection@ringoid.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getEmailSubject());
        intent.putExtra(Intent.EXTRA_TEXT, String.format(context.getString(R.string.message_protection_officer), customerId));

        if (intent.resolveActivity(context.getPackageManager()) == null) return;

        context.startActivity(intent);
    }

    @Override
    public void navigateUrlExternal(Context context, String url) {
        onPageShow(PAGE_ENUM.WEBVIEW_EXTERNAL);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        if (intent.resolveActivity(context.getPackageManager()) == null) return;

        context.startActivity(intent);
    }

    @Override
    public void navigateSettingsPush() {
        onPageShow(PAGE_ENUM.SETTINGS_PUSH);
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsPush(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }
}
