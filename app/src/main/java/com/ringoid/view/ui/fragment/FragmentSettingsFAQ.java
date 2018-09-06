/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterSettingsFAQ;

import javax.inject.Inject;

public class FragmentSettingsFAQ extends FragmentBase
        implements View.OnClickListener {

    private static final String ARG_SHOW_ALL = "arg_show_all";

    @Inject
    INavigator navigator;

    @Inject
    IPresenterSettingsFAQ presenterSettingsPrivacy;

    public static Fragment getInstance(boolean showAll) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_ALL, showAll);
        Fragment fragment = new FragmentSettingsFAQ();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_faq, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(R.string.settings_privacy_subtitle);

        view.findViewById(R.id.tvPrivacyPhotos).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyPhone).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyLocation).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyAge).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyActivity).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyMessages).setOnClickListener(this);

        setVisibility(view);

        presenterSettingsPrivacy.onCreateView();
    }

    private void setVisibility(View view) {
        view.findViewById(R.id.llPrivacyPhotos).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_ALL) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.llPrivacyPhone).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_ALL) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.llPrivacyLocation).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_ALL) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.llPrivacyAge).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_ALL) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.llPrivacyActivity).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_ALL) ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.llPrivacyMessages).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_ALL) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        visibilityChangeCheck(v, R.id.tvPrivacyPhotos, R.id.llPrivacyPhotos);
        visibilityChangeCheck(v, R.id.tvPrivacyPhone, R.id.llPrivacyPhone);
        visibilityChangeCheck(v, R.id.tvPrivacyLocation, R.id.llPrivacyLocation);
        visibilityChangeCheck(v, R.id.tvPrivacyAge, R.id.llPrivacyAge);
        visibilityChangeCheck(v, R.id.tvPrivacyActivity, R.id.llPrivacyActivity);
        visibilityChangeCheck(v, R.id.tvPrivacyMessages, R.id.llPrivacyMessages);
    }

    private void visibilityChangeCheck(View v, int viewId, int viewContainerId) {
        if (v.getId() != viewId) return;
        View vContainer = getView().findViewById(viewContainerId);
        vContainer.setVisibility(vContainer.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        TextView view = getView().findViewById(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(view.getCompoundDrawables()[0], null, getContext().getResources().getDrawable(vContainer.getVisibility() == View.GONE
                ? R.drawable.ic_keyboard_arrow_down_gray_24dp
                : R.drawable.ic_keyboard_arrow_up_black_24dp), null);
    }
}
