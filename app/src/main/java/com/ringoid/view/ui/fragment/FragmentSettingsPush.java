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
import android.widget.ImageView;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterSettingsPush;
import com.ringoid.view.presenter.callback.IPresenterSettingsPushListener;

import javax.inject.Inject;

public class FragmentSettingsPush extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterSettingsPush presenterSettingsPush;

    private ImageView ivMatches, ivMessages, ivLikes;
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
        ivMatches = view.findViewById(R.id.ivPushMatches);
        ivMessages = view.findViewById(R.id.ivPushMessages);
        ivLikes = view.findViewById(R.id.ivPushLikes);

        llLikesTypes = view.findViewById(R.id.llLikesTypes);
        tvLikesAll = view.findViewById(R.id.tvPushLikesAll);
        tvLikes10 = view.findViewById(R.id.tvPushLikesEvery10);
        tvLikes100 = view.findViewById(R.id.tvPushLikesEvery100);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.ivPushLikes).setOnClickListener(this);
        view.findViewById(R.id.ivPushMatches).setOnClickListener(this);
        view.findViewById(R.id.ivPushMessages).setOnClickListener(this);

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
        checkSwitch(v, R.id.ivPushLikes);
        checkSwitch(v, R.id.ivPushMatches);
        checkSwitch(v, R.id.ivPushMessages);

        checkLikesSelected(v, R.id.tvPushLikesAll);
        checkLikesSelected(v, R.id.tvPushLikesEvery10);
        checkLikesSelected(v, R.id.tvPushLikesEvery100);
    }

    private void checkLikesSelected(View v, int resId) {
        if (v.getId() != resId) return;
        presenterSettingsPush.onSelectLikesType(resId);
    }

    private void checkSwitch(View v, int viewId) {
        if (v.getId() != viewId) return;
        presenterSettingsPush.updateChecked(viewId);
    }

    private void setPushLikes(int type) {
        ivLikes.setImageResource(type != 0 ? R.drawable.switch_on : R.drawable.switch_off);
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

    private class ListenerPresenter implements IPresenterSettingsPushListener {
        @Override
        public void setData(boolean messages, boolean matches, int pushLikesType) {
            if (getContext() == null) return;
            ivMessages.setImageResource(messages ? R.drawable.switch_on : R.drawable.switch_off);
            ivMatches.setImageResource(matches ? R.drawable.switch_on : R.drawable.switch_off);
            setPushLikes(pushLikesType);
        }
    }
}
