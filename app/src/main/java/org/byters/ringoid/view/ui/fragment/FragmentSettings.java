package org.byters.ringoid.view.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.BuildConfig;
import org.byters.ringoid.R;
import org.byters.ringoid.view.INavigator;

import javax.inject.Inject;

public class FragmentSettings extends FragmentBase
        implements View.OnClickListener {

    @Inject
    INavigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
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

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_subtitle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        if (v.getId() == R.id.tvPrivacy)
            navigator.navigateSettingsPrivacy(false);

        if (v.getId() == R.id.tvSettingsTerms)
            navigator.navigateWebView("http://ringoid.com/terms.html");

        if (v.getId() == R.id.tvSettingsFeedback)
            navigator.navigateFeedback(getContext(),
                    BuildConfig.VERSION_CODE,
                    BuildConfig.VERSION_NAME,
                    Build.VERSION.RELEASE + ", " + Build.VERSION.SDK_INT,
                    Build.MODEL + " " + Build.MANUFACTURER + " " + Build.PRODUCT);
    }
}
