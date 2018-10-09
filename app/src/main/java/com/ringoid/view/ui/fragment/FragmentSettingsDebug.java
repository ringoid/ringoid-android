package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.network.response.ResponseBase;
import com.ringoid.view.ui.util.ApiRingoidProvider;
import com.ringoid.view.ui.util.IHelperScreenshots;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class FragmentSettingsDebug extends FragmentBase implements View.OnClickListener {

    @Inject
    IHelperScreenshots helperScreenshots;

    @Inject
    ApiRingoidProvider providerApi;

    private TextView tvScreenshots;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_debug, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        tvScreenshots = view.findViewById(R.id.tvScreenshots);
        tvScreenshots.setOnClickListener(this);

        view.findViewById(R.id.tvTestTimeout).setOnClickListener(this);
        view.findViewById(R.id.tvTestResponseNot200).setOnClickListener(this);
        view.findViewById(R.id.tvTestTokenInvalid).setOnClickListener(this);
        view.findViewById(R.id.tvTestAppVersionError).setOnClickListener(this);

        updateStateScreenshots();
    }

    private void updateStateScreenshots() {
        tvScreenshots.setText(helperScreenshots.isScreenshotsSecured() ? "Screenshots state:disabled" : "Screenshots state: enabled");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvScreenshots) {
            helperScreenshots.changeStateScreenshots();
            updateStateScreenshots();
        }

        if (v.getId() == R.id.tvTestTimeout)
            providerApi.getAPI().testTimeout().enqueue(new ListenerBase());

        if (v.getId() == R.id.tvTestResponseNot200)
            providerApi.getAPI().testResponseNot200().enqueue(new ListenerBase());

        if (v.getId() == R.id.tvTestTokenInvalid)
            providerApi.getAPI().testTokenInvalid().enqueue(new ListenerBase());

        if (v.getId() == R.id.tvTestAppVersionError)
            providerApi.getAPI().testAppVersion().enqueue(new ListenerBase());
    }

    private static class ListenerBase implements retrofit2.Callback<com.ringoid.controller.data.network.response.ResponseBase> {
        @Override
        public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {

        }

        @Override
        public void onFailure(Call<ResponseBase> call, Throwable t) {

        }
    }
}
