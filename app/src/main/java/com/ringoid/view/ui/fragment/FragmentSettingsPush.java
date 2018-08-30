/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterSettingsPush;
import com.ringoid.view.presenter.callback.IPresenterSettingsPushListener;

import javax.inject.Inject;

public class FragmentSettingsPush extends FragmentBase implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Inject
    IPresenterSettingsPush presenterSettingsPush;

    private Switch swMatches, swMessages, swLikes;
    private View llLikesTypes;
    private TextView tvLikesAll, tvLikes10, tvLikes100;
    private IPresenterSettingsPushListener listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterSettingsPush.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_push, container, false);

        initView(view);
        presenterSettingsPush.onCreateView();
        return view;
    }

    private void initView(View view) {
        swMatches = view.findViewById(R.id.swPushMatches);
        swMessages = view.findViewById(R.id.swPushMessages);
        swLikes = view.findViewById(R.id.swPushLikes);

        llLikesTypes = view.findViewById(R.id.llLikesTypes);
        tvLikesAll = view.findViewById(R.id.tvPushLikesAll);
        tvLikes10 = view.findViewById(R.id.tvPushLikesEvery10);
        tvLikes100 = view.findViewById(R.id.tvPushLikesEvery100);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        swLikes.setOnCheckedChangeListener(this);
        swMatches.setOnCheckedChangeListener(this);
        swMessages.setOnCheckedChangeListener(this);

        view.findViewById(R.id.tvPushLikesAll).setOnClickListener(this);
        view.findViewById(R.id.tvPushLikesEvery10).setOnClickListener(this);
        view.findViewById(R.id.tvPushLikesEvery100).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_push_subtitle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        checkLikesSelected(v, R.id.tvPushLikesAll);
        checkLikesSelected(v, R.id.tvPushLikesEvery10);
        checkLikesSelected(v, R.id.tvPushLikesEvery100);
    }

    private void checkLikesSelected(View v, int resId) {
        if (v.getId() != resId) return;
        presenterSettingsPush.onSelectLikesType(resId);
    }

    private void setPushLikes(int type) {
        swLikes.setChecked(type != 0);
        llLikesTypes.setVisibility(type != 0 ? View.VISIBLE : View.GONE);

        checkSelected(tvLikesAll, type == 1);
        checkSelected(tvLikes10, type == 2);
        checkSelected(tvLikes100, type == 3);
    }

    private void checkSelected(TextView textView, boolean isSelected) {
        textView.setTypeface(null, isSelected ? Typeface.BOLD : Typeface.NORMAL);
        Drawable drawable = isSelected ? ContextCompat.getDrawable(getContext(), R.drawable.ic_check_green_24dp) : null;
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkChecked(buttonView, swMessages);
        checkChecked(buttonView, swMatches);
        checkChecked(buttonView, swLikes);
    }

    private void checkChecked(CompoundButton buttonView, Switch viewSwitch) {
        if (buttonView.getId() != viewSwitch.getId()) return;

        presenterSettingsPush.setChecked(viewSwitch.getId(), viewSwitch.isChecked());
    }

    private class ListenerPresenter implements IPresenterSettingsPushListener {
        @Override
        public void setData(boolean messages, boolean matches, int pushLikesType) {
            if (getContext() == null) return;
            swMessages.setChecked(messages);
            swMatches.setChecked(matches);
            setPushLikes(pushLikesType);
        }
    }
}
