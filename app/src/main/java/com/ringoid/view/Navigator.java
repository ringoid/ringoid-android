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
import com.ringoid.view.ui.fragment.FragmentErrorAppversion;
import com.ringoid.view.ui.fragment.FragmentErrorConnection;
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

    @Override
    public void navigateFeed() {
        clearBackStack();

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentPages())
                .commit();
    }

    @Override
    public void navigateLogin() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        clearBackStack();

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentLogin.getInstancePhoneInput(), CURRENT_FRAGMENT_PAGE)
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
    public void navigateBlacklistPhones() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentBlacklistPhones(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateBlacklistPhonesAdd() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
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
        if (requestCode != SELECT_FILE) return;
        if (data != null) {
            navigatePhotoCrop(data.getData());
            return;
        }
        if (isCurrentPageLogin())
            navigateFeed();
    }

    private boolean isCurrentPageLogin() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return false;
        Fragment fragment = refFragmentManager.get().findFragmentByTag(CURRENT_FRAGMENT_PAGE);
        return fragment instanceof FragmentLogin;
    }

    @Override
    public void navigateProfileUpdate() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        clearBackStack();

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentLogin.getInstanceProfileUpdate(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateRegisterCodeConfirm() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        clearBackStack();

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentLogin.getInstanceCodeConfirm(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateFeedProfilePhotoSelect() {
        clearBackStack();

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentPages.getInstanceProfilePhotoUpload())
                .commit();
    }

    @Override
    public void navigateSettingsDataProtection() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentDataProtection(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateScreenDebug() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsDebug(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void onResume(FragmentBase fragmentBase) {

        PAGE_ENUM page = fragmentBase.getPage();

        if (page == PAGE_ENUM.FEED_LIKES
                || page == PAGE_ENUM.FEED_MESSAGES
                || page == PAGE_ENUM.FEED_EXPLORE
                || page == PAGE_ENUM.FEED_MATCHES
                || page == PAGE_ENUM.CHAT)
            helperScreenshots.disableScreenshots();
        else
            helperScreenshots.enableScreenshots();

        if (page == PAGE_ENUM.FEED_PROFILE
                || page == PAGE_ENUM.FEED_LIKES
                || page == PAGE_ENUM.FEED_MESSAGES
                || page == PAGE_ENUM.FEED_EXPLORE
                || page == PAGE_ENUM.FEED_SETTINGS
                || page == PAGE_ENUM.FEED_MATCHES)
            helperFullscreen.statusbarShowFullscreen();
        else
            helperFullscreen.statusbarShowResizeable();
    }

    @Override
    public void finish() {
        if (refActivity == null || refActivity.get() == null) return;
        refActivity.get().finish();
    }

    @Override
    public void navigateErrorConnection() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentErrorConnection(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateErrorAppversion() {
        clearBackStack();

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentErrorAppversion(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    private void navigatePhotoCrop(Uri data) {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, FragmentPhotoCrop.getInstance(data), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigatePhotoAdd() {
        if (refActivity == null || refActivity.get() == null) return;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (intent.resolveActivity(refActivity.get().getPackageManager()) == null) return;

        refActivity.get().startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void navigateChat() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentChat(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateSettingsPrivacyDistance() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsPrivacyDistance(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateWebView(String url, String subtitle) {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentWebView.getInstance(url, subtitle), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateFeedback(Context context) {
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
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:data.protection@ringoid.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getEmailSubject());
        intent.putExtra(Intent.EXTRA_TEXT, String.format(context.getString(R.string.message_protection_officer), customerId));

        if (intent.resolveActivity(context.getPackageManager()) == null) return;

        context.startActivity(intent);
    }

    @Override
    public void navigateUrlExternal(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        if (intent.resolveActivity(context.getPackageManager()) == null) return;

        context.startActivity(intent);
    }

    @Override
    public void navigateSettingsPush() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsPush(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }
}
