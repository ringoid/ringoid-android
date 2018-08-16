/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steelkiwi.cropiwa.CropIwaView;

import com.ringoid.R;

public class FragmentPhotoCrop extends FragmentBase implements View.OnClickListener {

    private static String ARG_URL = "arg_url";

    public static Fragment getInstance(Uri url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url.toString());

        FragmentBase fragmentBase = new FragmentPhotoCrop();
        fragmentBase.setArguments(bundle);
        return fragmentBase;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_crop, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initImage(view);
        view.findViewById(R.id.ivCropConfirm).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.crop_subtitle);
    }

    private void initImage(View view) {
        String url = getArguments().getString(ARG_URL);
        if (TextUtils.isEmpty(url)) return;

        CropIwaView cropImageView = view.findViewById(R.id.crop_view);

        cropImageView.setImageUri(Uri.parse(url));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCropConfirm) {
            getActivity().onBackPressed();
        }
    }
}
