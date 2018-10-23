/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.innovattic.rangeseekbar.RangeSeekBar;
import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.view.INavigator;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.IPresenterSettings;
import com.ringoid.view.presenter.callback.IPresenterSettingsListener;
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

    @Inject
    IPresenterSettings presenterSettings;

    private DialogLogout dialogLogout;
    private DialogAccountDelete dialogAccountDelete;
    private IDialogLogoutListener listenerDialogLogout;
    private ListenerPresenterSettings listenerPresenter;
    private ScrollView svContent;
    private RangeSeekBar seekbarAge;

    @Override
    public PAGE_ENUM getPage() {
        return PAGE_ENUM.FEED_SETTINGS;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogLogout = new ListenerDialogLogout();
        presenterSettings.setListener(listenerPresenter = new ListenerPresenterSettings());

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

        view.findViewById(R.id.tvDataProtection).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsFeedback).setOnClickListener(this);
        view.findViewById(R.id.tvLogout).setOnClickListener(this);
        view.findViewById(R.id.tvAccountDelete).setOnClickListener(this);
        view.findViewById(R.id.ivClose).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);

        svContent = view.findViewById(R.id.svContent);
        svContent.getViewTreeObserver().addOnScrollChangedListener(new ListenerScroll());

        view.findViewById(R.id.sbDistance).setEnabled(false);
        seekbarAge = view.findViewById(R.id.vrAge).findViewById(R.id.rangeSeekBar);
        seekbarAge.setSeekBarChangeListener(new DisabledSeekbarListener());
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tvDataProtection)
            navigator.navigateSettingsDataProtection();

        if (v.getId() == R.id.tvSettingsFeedback)
            navigator.navigateFeedback(getContext());

        if (v.getId() == R.id.tvLogout)
            showDialogLogout();

        if (v.getId() == R.id.tvAccountDelete)
            showDialogAccountDelete();

        if (v.getId() == R.id.ivClose
                || v.getId() == R.id.ivBack)
            getActivity().onBackPressed();
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
        public void scrollToPosition(int firstVisiblePosition, final int offset) {
            if (getContext() == null) return;
            svContent.post(new Runnable() {
                @Override
                public void run() {
                    svContent.scrollTo(0, offset);
                }
            });
        }

        @Override
        public boolean isPositionTop() {
            return svContent.getScrollY() == 0;
        }
    }

    private class ListenerScroll implements ViewTreeObserver.OnScrollChangedListener {
        @Override
        public void onScrollChanged() {
            presenterSettings.onScroll(svContent.getScrollY());
        }
    }

    private class DisabledSeekbarListener implements RangeSeekBar.SeekBarChangeListener {
        @Override
        public void onStartedSeeking() {

        }

        @Override
        public void onStoppedSeeking() {

        }

        @Override
        public void onValueChanged(int i, int i1) {
            if (seekbarAge.getMaxThumbValue() != 100) seekbarAge.setMaxThumbValue(100);
            if (seekbarAge.getMinThumbValue() != 0) seekbarAge.setMinThumbValue(0);
        }
    }
}
