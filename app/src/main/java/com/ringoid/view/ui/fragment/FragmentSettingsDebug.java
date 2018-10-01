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
import com.ringoid.view.ui.util.IHelperScreenshots;

import javax.inject.Inject;

public class FragmentSettingsDebug extends FragmentBase implements View.OnClickListener {

    @Inject
    IHelperScreenshots helperScreenshots;
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

        updateStateScreenshots();
    }

    private void updateStateScreenshots() {
        tvScreenshots.setText(helperScreenshots.isScreenshotsSecured(getActivity().getWindow()) ? "Screenshots state:disabled" : "Screenshots state: enabled");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvScreenshots) {
            helperScreenshots.changeStateScreenshots(getActivity().getWindow());
            updateStateScreenshots();
        }
    }
}
