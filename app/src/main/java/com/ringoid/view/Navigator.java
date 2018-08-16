/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ringoid.view.ui.fragment.FragmentBase;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhonesAdd;
import com.ringoid.view.ui.fragment.FragmentChat;
import com.ringoid.view.ui.fragment.FragmentLogin;
import com.ringoid.view.ui.fragment.FragmentPages;
import com.ringoid.view.ui.fragment.FragmentPhotoCrop;
import com.ringoid.view.ui.fragment.FragmentSettings;
import com.ringoid.view.ui.fragment.FragmentSettingsPrivacy;
import com.ringoid.view.ui.fragment.FragmentSettingsPrivacyDistance;
import com.ringoid.view.ui.fragment.FragmentSettingsPush;
import com.ringoid.view.ui.fragment.FragmentWebView;
import com.ringoid.view.ui.fragment.FragmentWelcome;

import java.lang.ref.WeakReference;

public class Navigator implements INavigator {
    private static final String CURRENT_FRAGMENT_PAGE = "current_fragment";
    private static final int SELECT_FILE = 173;

    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

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
                .addToBackStack(null)
                .replace(viewId, new FragmentLogin(), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    private void clearBackStack() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        FragmentManager fm = refFragmentManager.get();

        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void set(FragmentManager supportFragmentManager, int viewId) {
        this.refFragmentManager = new WeakReference<>(supportFragmentManager);
        this.viewId = viewId;
    }

    @Override
    public void navigateSettings() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettings(), CURRENT_FRAGMENT_PAGE)
                .commit();
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
        if (requestCode == SELECT_FILE) {
            navigatePhotoCrop(data.getData());
        }
    }

    private void navigatePhotoCrop(Uri data) {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentPhotoCrop.getInstance(data), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigatePhotoAdd(AppCompatActivity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (intent.resolveActivity(activity.getPackageManager()) == null) return;

        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
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
    public void navigateSettingsPrivacy(boolean showPhoto) {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentSettingsPrivacy.getInstance(showPhoto), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateWebView(String url) {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentWebView.getInstance(url), CURRENT_FRAGMENT_PAGE)
                .commit();
    }

    @Override
    public void navigateFeedback(Context context, int versionCode, String versionName, String sdkInt, String model) {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@ringoid.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, String.format("Ringoid Android App %d(%s) Feedback %s, %s", versionCode, versionName, model, sdkInt));
        intent.putExtra(Intent.EXTRA_TEXT, "");

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

    @Override
    public void navigateWelcome(boolean isLogin) {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        FragmentTransaction transaction = refFragmentManager.get()
                .beginTransaction();

        if (!isLogin)
            transaction.addToBackStack(null);

        transaction.replace(viewId, FragmentWelcome.getInstance(isLogin), CURRENT_FRAGMENT_PAGE)
                .commit();
    }
}
