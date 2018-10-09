package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.util.IHelperConnection;

import javax.inject.Inject;

public class FragmentErrorConnection extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IHelperConnection helperConnection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error_connection, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        view.findViewById(R.id.tvButtonRefresh).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (helperConnection.isConnectionExist())
            getActivity().onBackPressed();
    }
}
