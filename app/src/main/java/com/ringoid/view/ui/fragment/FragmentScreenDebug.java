package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.R;

public class FragmentScreenDebug extends FragmentBase {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_debug, container, false);
        return view;
    }
}
