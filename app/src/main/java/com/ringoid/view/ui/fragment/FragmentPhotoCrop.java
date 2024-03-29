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
import com.ringoid.view.ui.util.GlideApp;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.OnCropUpdateListener;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;

import java.io.File;

import javax.inject.Inject;

public class FragmentPhotoCrop extends FragmentBase implements View.OnClickListener {

    private static String ARG_URL = "arg_url";

    @Inject
    IPresenterPhotoCrop presenterPhotoCrop;

    private CropIwaView cropImageView;
    private ListenerPresenter listenerPresenter;
    private View vCropConfirm;
    private ListenerCrop listenerCrop;

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
        return view;
    }

    private void initViews(View view) {
        initImage(view);
        vCropConfirm = view.findViewById(R.id.tvCropConfirm);
        vCropConfirm.setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.ivClose).setOnClickListener(this);

    }

    private void setViewConfirmWidth() {

        ViewGroup.LayoutParams params = vCropConfirm.getLayoutParams();

        params.width = cropImageView.getCropiwaCropWidth();

        vCropConfirm.setLayoutParams(params);
    }

    private void initImage(View view) {
        String url = getArguments().getString(ARG_URL);
        if (TextUtils.isEmpty(url)) return;

        cropImageView = view.findViewById(R.id.crop_view);
        cropImageView.setListenerCropUpdate(new ListenerCropUpdate());
        cropImageView.setImageUri(Uri.parse(url));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvCropConfirm)
            presenterPhotoCrop.onCLickCrop();

        if (v.getId() == R.id.ivBack
                || v.getId() == R.id.ivClose) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (listenerCrop != null && listenerCrop.isCropping) return true;
        return presenterPhotoCrop.onBackPressed();
    }

    private class ListenerCrop implements CropIwaView.CropSaveCompleteListener {

        private File file;
        private boolean isCropping;

        ListenerCrop(File file) {
            this.file = file;
            isCropping = true;
        }

        @Override
        public void onCroppedRegionSaved(Uri bitmapUri) {
            presenterPhotoCrop.onCropCompleted(file);
            GlideApp.with(getContext()).load(file).preload();
            isCropping = false;
            listenerCrop = null;
        }
    }

    private class ListenerPresenter implements IPresenterPhotoCropListener {

        @Override
        public void crop() {
            File file = new File(
                    getContext().getFilesDir(),
                    System.currentTimeMillis() + ".jpg");

            cropImageView.setCropSaveCompleteListener(listenerCrop = new ListenerCrop(file));
            cropImageView.crop(new CropIwaSaveConfig.Builder(Uri.fromFile(file))
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setQuality(100)
                    .build());
        }
    }

    private class ListenerCropUpdate implements OnCropUpdateListener {
        @Override
        public void onUpdate() {
            setViewConfirmWidth();
        }
    }
}
