/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterPhotoCrop;
import com.ringoid.view.presenter.IPresenterPhotoCropListener;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;

import java.io.File;

import javax.inject.Inject;

public class FragmentPhotoCrop extends FragmentBase implements View.OnClickListener {

    private static String ARG_URL = "arg_url";

    @Inject
    IPresenterPhotoCrop presenterPhotoCrop;

    private CropIwaView cropImageView;
    private ListenerPresenter listenerPresenter;

    public static Fragment getInstance(Uri url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url.toString());

        FragmentBase fragmentBase = new FragmentPhotoCrop();
        fragmentBase.setArguments(bundle);
        return fragmentBase;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterPhotoCrop.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_crop, container, false);
        initViews(view);
        presenterPhotoCrop.onCreateView();
        return view;
    }

    private void initViews(View view) {
        initImage(view);
        view.findViewById(R.id.tvCropConfirm).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);
    }

    private void initImage(View view) {
        String url = getArguments().getString(ARG_URL);
        if (TextUtils.isEmpty(url)) return;

        cropImageView = view.findViewById(R.id.crop_view);

        cropImageView.setImageUri(Uri.parse(url));
        cropImageView.setCropSaveCompleteListener(new ListenerCrop());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvCropConfirm) {
            onClickCrop();
        }
        if (v.getId() == R.id.ivBack) {
            getActivity().onBackPressed();
        }
    }

    private void onClickCrop() {
        File file = new File(
                getContext().getFilesDir(),
                System.currentTimeMillis() + ".jpg");
        presenterPhotoCrop.setFile(file);

        cropImageView.crop(new CropIwaSaveConfig.Builder(Uri.fromFile(file))
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setQuality(100)
                .build());
    }

    private class ListenerCrop implements CropIwaView.CropSaveCompleteListener {
        @Override
        public void onCroppedRegionSaved(Uri bitmapUri) {
            presenterPhotoCrop.onClickCrop(bitmapUri);
            getActivity().onBackPressed();
        }
    }

    private class ListenerPresenter implements IPresenterPhotoCropListener {

    }
}
