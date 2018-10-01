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
import com.ringoid.view.ui.dialog.DialogPrivacyLikes;
import com.ringoid.view.ui.dialog.DialogPrivacyPhotos;
import com.ringoid.view.ui.dialog.callback.IDialogLogoutListener;
import com.ringoid.view.ui.dialog.callback.IDialogPrivacyLikesListener;
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
    private TextView tvPrivacyPhotos, tvPrivacyDistance, tvPrivacyPhoneBlacklistNum, tvPrivacyLikes;
    private DialogPrivacyPhotos dialogPrivacyPhotos;
    private DialogPrivacyLikes dialogPrivacyLikes;
    private IDialogPrivacyPhotosListener listenerDialogPrivacyPhotos;
    private IDialogPrivacyLikesListener listenerDialogPrivacyLikes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogLogout = new ListenerDialogLogout();
        presenterSettings.setListener(listenerPresenter = new ListenerPresenterSettings());
        listenerDialogPrivacyPhotos = new ListenerDialogPrivacyPhotos();
        listenerDialogPrivacyLikes = new ListenerDialogPrivacyLikes();

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
        tvPrivacyLikes = view.findViewById(R.id.tvPrivacyLikes);
        tvPrivacyPhotos = view.findViewById(R.id.tvPrivacyPhotos);
        tvPrivacyPhoneBlacklistNum = view.findViewById(R.id.tvPrivacyPhoneBlacklistNum);
        tvPrivacyDistance = view.findViewById(R.id.tvPrivacyDistance);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.tvDataProtection).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsFeedback).setOnClickListener(this);
        view.findViewById(R.id.tvLogout).setOnClickListener(this);
        view.findViewById(R.id.tvAccountDelete).setOnClickListener(this);
        view.findViewById(R.id.tvPush).setOnClickListener(this);

        view.findViewById(R.id.llPrivacyLikes).setOnClickListener(this);
        view.findViewById(R.id.llPrivacyPhotos).setOnClickListener(this);
        view.findViewById(R.id.llBlacklist).setOnClickListener(this);
        view.findViewById(R.id.llPrivacyDistance).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_subtitle);

        if (BuildConfig.DEBUG) {
            View vDebug = view.findViewById(R.id.tvDebug);
            vDebug.setVisibility(View.VISIBLE);
            vDebug.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        if (v.getId() == R.id.llPrivacyLikes)
            showDialogPrivacyLikes();

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

        if (v.getId() == R.id.tvSettingsFeedback)
            navigator.navigateFeedback(getContext());

        if (v.getId() == R.id.tvLogout)
            showDialogLogout();

        if (v.getId() == R.id.tvAccountDelete)
            showDialogAccountDelete();

        if (v.getId() == R.id.tvDebug) {
            if (BuildConfig.DEBUG)
                navigator.navigateScreenDebug();
        }
    }

    private void showDialogPrivacyLikes() {
        if (getContext() == null) return;
        if (dialogPrivacyLikes != null) dialogPrivacyLikes.cancel();
        dialogPrivacyLikes = new DialogPrivacyLikes(getContext(), listenerDialogPrivacyLikes);
        dialogPrivacyLikes.show();
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
        public void setPrivacyLikes(int selected) {
            if (getContext() == null) return;
            tvPrivacyLikes.setText(selected == 0 ? R.string.privacy_likes_all : R.string.privacy_likes_matches);
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

    private class ListenerDialogPrivacyLikes implements IDialogPrivacyLikesListener {
        @Override
        public void onClickMatches() {
            presenterSettings.onClickPrivacyLikesMatches();
        }

        @Override
        public void onClickLiked() {
            presenterSettings.onClickPrivacyLikesLiked();
        }
    }
}
