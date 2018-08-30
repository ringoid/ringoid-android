/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterSettingsPrivacyDistance;
import com.ringoid.view.presenter.callback.IPresenterSettingsPrivacyDistanceListener;

import javax.inject.Inject;

public class FragmentSettingsPrivacyDistance extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterSettingsPrivacyDistance presenterSettingsPrivacyDistance;
    private TextView tvDistance0, tvDistance1, tvDistance2, tvDistance25, tvDistance3, tvDistance4;
    private IPresenterSettingsPrivacyDistanceListener listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterSettingsPrivacyDistance.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_privacy_distance, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(R.string.privacy_distance_subtitle);

        tvDistance0 = view.findViewById(R.id.tvDistance0);
        tvDistance1 = view.findViewById(R.id.tvDistance1);
        tvDistance25 = view.findViewById(R.id.tvDistance25);
        tvDistance2 = view.findViewById(R.id.tvDistance2);
        tvDistance3 = view.findViewById(R.id.tvDistance3);
        tvDistance4 = view.findViewById(R.id.tvDistance4);

        tvDistance0.setOnClickListener(this);
        tvDistance1.setOnClickListener(this);
        tvDistance2.setOnClickListener(this);
        tvDistance25.setOnClickListener(this);
        tvDistance3.setOnClickListener(this);
        tvDistance4.setOnClickListener(this);

        presenterSettingsPrivacyDistance.onCreateView();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        checkClickDistance(v, R.id.tvDistance0);
        checkClickDistance(v, R.id.tvDistance1);
        checkClickDistance(v, R.id.tvDistance25);
        checkClickDistance(v, R.id.tvDistance2);
        checkClickDistance(v, R.id.tvDistance3);
        checkClickDistance(v, R.id.tvDistance4);

    }

    private void checkClickDistance(View v, int viewId) {
        if (v.getId() != viewId) return;
        presenterSettingsPrivacyDistance.onClickDistance(viewId);
    }

    private class ListenerPresenter implements IPresenterSettingsPrivacyDistanceListener {
        @Override
        public void setDistance(int resId) {
            if (getContext() == null || resId == 0) return;

            tvDistance0.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == R.id.tvDistance0 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance1.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == R.id.tvDistance1 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance25.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == R.id.tvDistance25 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance2.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == R.id.tvDistance2 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance3.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == R.id.tvDistance3 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvDistance4.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == R.id.tvDistance4 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);

            checkBold(tvDistance0, resId);
            checkBold(tvDistance1, resId);
            checkBold(tvDistance2, resId);
            checkBold(tvDistance3, resId);
            checkBold(tvDistance4, resId);

        }

        private void checkBold(TextView textView, int resId) {
            textView.setTypeface(null, textView.getId() == resId ? Typeface.BOLD : Typeface.NORMAL);
        }
    }
}
