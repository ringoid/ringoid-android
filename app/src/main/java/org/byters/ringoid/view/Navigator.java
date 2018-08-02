package org.byters.ringoid.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhonesAdd;
import org.byters.ringoid.view.ui.fragment.FragmentChat;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;
import org.byters.ringoid.view.ui.fragment.FragmentPages;
import org.byters.ringoid.view.ui.fragment.FragmentSettings;
import org.byters.ringoid.view.ui.fragment.FragmentSettingsPrivacy;
import org.byters.ringoid.view.ui.fragment.FragmentSettingsPrivacyDistance;
import org.byters.ringoid.view.ui.fragment.FragmentSettingsPush;
import org.byters.ringoid.view.ui.fragment.FragmentWebView;
import org.byters.ringoid.view.ui.fragment.FragmentWelcome;

import java.lang.ref.WeakReference;

public class Navigator implements INavigator {
    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;

    @Override
    public void navigateFeed() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentPages())
                .commit();
    }

    @Override
    public void navigateLogin() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentLogin())
                .commit();
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
                .replace(viewId, new FragmentSettings())
                .commit();
    }

    @Override
    public void navigateBlacklistPhones() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentBlacklistPhones())
                .commit();
    }

    @Override
    public void navigateBlacklistPhonesAdd() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentBlacklistPhonesAdd())
                .commit();
    }


    @Override
    public void navigateChat() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentChat())
                .commit();
    }

    @Override
    public void navigateSettingsPrivacyDistance() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentSettingsPrivacyDistance())
                .commit();
    }

    @Override
    public void navigateSettingsPrivacy(boolean showPhoto) {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentSettingsPrivacy.getInstance(showPhoto))
                .commit();
    }

    @Override
    public void navigateWebView(String url) {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, FragmentWebView.getInstance(url))
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
                .replace(viewId, new FragmentSettingsPush())
                .commit();
    }

    @Override
    public void navigateWelcome() {

        if (refFragmentManager == null || refFragmentManager.get() == null) return;
        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .replace(viewId, new FragmentWelcome())
                .commit();
    }
}
