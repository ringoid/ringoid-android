/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.util.ILogoutHelper;
import com.ringoid.view.ui.dialog.DialogAccountDelete;
import com.ringoid.view.ui.dialog.DialogLogout;
import com.ringoid.view.ui.dialog.callback.IDialogLogoutListener;

import javax.inject.Inject;

public class FragmentSettings extends FragmentBase
        implements View.OnClickListener {

    @Inject
    INavigator navigator;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ILogoutHelper logoutHelper;

    private DialogLogout dialogLogout;
    private DialogAccountDelete dialogAccountDelete;
    private IDialogLogoutListener listenerDialogLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogLogout = new ListenerDialogLogout();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacy).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsTerms).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsFeedback).setOnClickListener(this);
        view.findViewById(R.id.tvLogout).setOnClickListener(this);
        view.findViewById(R.id.tvAccountDelete).setOnClickListener(this);
        view.findViewById(R.id.tvPush).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsAbout).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsLicenses).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_subtitle);

        TextView tvVersion = view.findViewById(R.id.tvVersion);
        tvVersion.setText(String.format("v %d (%s)", BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        if (v.getId() == R.id.tvPrivacy)
            navigator.navigateSettingsPrivacy(false);

        if (v.getId() == R.id.tvPush)
            navigator.navigateSettingsPush();

        if (v.getId() == R.id.tvSettingsTerms)
            navigator.navigateWebView(getContext().getString(R.string.url_terms), getContext().getString(R.string.subtitle_terms));


        if (v.getId() == R.id.tvSettingsLicenses)
            navigator.navigateWebView(getContext().getString(R.string.url_licenses), getContext().getString(R.string.subtitle_licenses));

        if (v.getId() == R.id.tvSettingsFeedback)
            navigator.navigateFeedback(getContext(),
                    BuildConfig.VERSION_CODE,
                    BuildConfig.VERSION_NAME,
                    Build.VERSION.RELEASE + ", " + Build.VERSION.SDK_INT,
                    Build.MODEL + " " + Build.MANUFACTURER + " " + Build.PRODUCT);

        if (v.getId() == R.id.tvLogout)
            showDialogLogout();

        if (v.getId() == R.id.tvAccountDelete)
            showDialogAccountDelete();

        if (v.getId() == R.id.tvSettingsAbout)
            navigator.navigateWelcome(false);
    }

    private void showDialogLogout() {
        if (dialogLogout != null)
            dialogLogout.cancel();
        dialogLogout = new DialogLogout(getContext(), listenerDialogLogout);
        dialogLogout.show();
    }

    private void showDialogAccountDelete() {
        if (dialogAccountDelete != null)
            dialogAccountDelete.cancel();
        dialogAccountDelete = new DialogAccountDelete(getContext());
        dialogAccountDelete.show();
    }

    private class ListenerDialogLogout implements IDialogLogoutListener {
        @Override
        public void onConfirm() {
            logoutHelper.logout();
        }
    }
}
