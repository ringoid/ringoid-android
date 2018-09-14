/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovattic.rangeseekbar.RangeSeekBar;
import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterSettings;
import com.ringoid.view.presenter.callback.IPresenterSettingsListener;
import com.ringoid.view.presenter.util.ILogoutHelper;
import com.ringoid.view.ui.dialog.DialogAccountDelete;
import com.ringoid.view.ui.dialog.DialogLogout;
import com.ringoid.view.ui.dialog.DialogPrivacyPhotos;
import com.ringoid.view.ui.dialog.callback.IDialogLogoutListener;
import com.ringoid.view.ui.dialog.callback.IDialogPrivacyPhotosListener;
import com.ringoid.view.ui.util.IStatusBarViewHelper;

import javax.inject.Inject;

public class FragmentSettings extends FragmentBase
        implements View.OnClickListener {

    @Inject
    INavigator navigator;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ILogoutHelper logoutHelper;

    @Inject
    IPresenterSettings presenterSettings;

    @Inject
    IStatusBarViewHelper statusBarViewHelper;

    private DialogLogout dialogLogout;
    private DialogAccountDelete dialogAccountDelete;
    private IDialogLogoutListener listenerDialogLogout;
    private ListenerPresenterSettings listenerPresenter;
    private TextView tvPrivacyPhotos, tvPrivacyDistance, tvPrivacyPhoneBlacklistNum, tvAge;
    private DialogPrivacyPhotos dialogPrivacyPhotos;
    private IDialogPrivacyPhotosListener listenerDialogPrivacyPhotos;
    private RangeSeekBar seekBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogLogout = new ListenerDialogLogout();
        presenterSettings.setListener(listenerPresenter = new ListenerPresenterSettings());
        listenerDialogPrivacyPhotos = new ListenerDialogPrivacyPhotos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initView(view);
        presenterSettings.onCreateView();
        return view;
    }

    private void initView(View view) {
        tvPrivacyPhotos = view.findViewById(R.id.tvPrivacyPhotos);
        tvPrivacyPhoneBlacklistNum = view.findViewById(R.id.tvPrivacyPhoneBlacklistNum);
        tvPrivacyDistance = view.findViewById(R.id.tvPrivacyDistance);
        tvAge = view.findViewById(R.id.tvAgeValue);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.tvDataProtection).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsTerms).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsFeedback).setOnClickListener(this);
        view.findViewById(R.id.tvLogout).setOnClickListener(this);
        view.findViewById(R.id.tvAccountDelete).setOnClickListener(this);
        view.findViewById(R.id.tvPush).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsLicenses).setOnClickListener(this);

        view.findViewById(R.id.llPrivacyPhotos).setOnClickListener(this);
        view.findViewById(R.id.llBlacklist).setOnClickListener(this);
        view.findViewById(R.id.llPrivacyDistance).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_subtitle);

        TextView tvVersion = view.findViewById(R.id.tvVersion);
        tvVersion.setText(String.format("v %d (%s)", BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME));

        seekBar = view.findViewById(R.id.sbAge);
        seekBar.setSeekBarChangeListener(new ListenerSeekBarChange());
        seekBar.setMax(56 - 18);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        if (v.getId() == R.id.llPrivacyPhotos)
            presenterSettings.onClickPrivacyPhotos();

        if (v.getId() == R.id.llBlacklist)
            presenterSettings.onClickPrivacyBlacklist();

        if (v.getId() == R.id.llPrivacyDistance)
            presenterSettings.onClickPrivacyDistance();

        if (v.getId() == R.id.tvDataProtection)
            navigator.navigateSettingsDataProtection();

        if (v.getId() == R.id.tvPush)
            navigator.navigateSettingsPush();

        if (v.getId() == R.id.tvSettingsTerms)
            navigator.navigateWebView(getContext().getString(R.string.url_terms), getContext().getString(R.string.subtitle_terms));


        if (v.getId() == R.id.tvSettingsLicenses)
            navigator.navigateWebView(getContext().getString(R.string.url_licenses), getContext().getString(R.string.subtitle_licenses));

        if (v.getId() == R.id.tvSettingsFeedback)
            navigator.navigateFeedback(getContext());

        if (v.getId() == R.id.tvLogout)
            showDialogLogout();

        if (v.getId() == R.id.tvAccountDelete)
            showDialogAccountDelete();
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

    private void setPrivacyAgeText(int min, int max) {
        String valueMax = max + 18 >= 56 ? "55+" : String.valueOf(max + 18);
        tvAge.setText(String.format("%d-%s", min + 18, valueMax));
    }

    private class ListenerDialogLogout implements IDialogLogoutListener {
        @Override
        public void onConfirm() {
            logoutHelper.logout();
        }
    }

    private class ListenerPresenterSettings implements IPresenterSettingsListener {

        @Override
        public void setPrivacyPhotos(int type) {
            if (getContext() == null) return;

            tvPrivacyPhotos.setText(type == 0 ? R.string.settings_privacy_photos_all
                    : type == 1 ? R.string.settings_privacy_photos_liked
                    : R.string.settings_privacy_photos_noone);

            statusBarViewHelper.setColor((AppCompatActivity) getActivity(), type);
        }

        @Override
        public void setPrivacyDistance(int distanceId) {
            if (getContext() == null) return;
            tvPrivacyDistance.setText(distanceId);
        }

        @Override
        public void setPrivacyPhoneNum(int itemsNum) {
            if (getContext() == null) return;
            tvPrivacyPhoneBlacklistNum.setText(String.format(getContext().getResources().getQuantityString(R.plurals.blacklist_phone_num, itemsNum), itemsNum));
        }

        @Override
        public void showDialogPrivacyPhotos(int selected) {
            if (getContext() == null) return;
            if (dialogPrivacyPhotos != null) dialogPrivacyPhotos.cancel();
            dialogPrivacyPhotos = new DialogPrivacyPhotos(getContext(), selected, listenerDialogPrivacyPhotos);
            dialogPrivacyPhotos.show();
        }

        @Override
        public void setPrivacyAge(int privacyAgeMin, int privacyAgeMax) {
            setPrivacyAgeText(privacyAgeMin, privacyAgeMax);
            seekBar.setMinThumbValue(privacyAgeMin - 18);
            seekBar.setMaxThumbValue(privacyAgeMax - 18);
        }
    }

    private class ListenerDialogPrivacyPhotos implements IDialogPrivacyPhotosListener {
        @Override
        public void onClickAll() {
            presenterSettings.onClickPrivacyPhotosAll();
        }

        @Override
        public void onClickLiked() {
            presenterSettings.onClickPrivacyPhotosLikes();
        }

        @Override
        public void onClickNoone() {
            presenterSettings.onClickPrivacyPhotosNoone();
        }

    }

    private class ListenerSeekBarChange implements RangeSeekBar.SeekBarChangeListener {
        @Override
        public void onStartedSeeking() {

        }

        @Override
        public void onStoppedSeeking() {

        }

        @Override
        public void onValueChanged(int min, int max) {
            setPrivacyAgeText(min, max);
            presenterSettings.onAgeSelected(min + 18, max + 18);
        }
    }
}
