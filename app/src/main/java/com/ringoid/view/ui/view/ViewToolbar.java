package com.ringoid.view.ui.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ringoid.R;

public class ViewToolbar extends FrameLayout {

    private static final String PARAM_SHOW_BACK = "param_show_back";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_SUBTITLE = "subtitle";

    public ViewToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public ViewToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ViewToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_toolbar, this);
        Bundle params = initAttrs(context, attrs);
        initViews(params);
    }

    private Bundle initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return null;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.toolbarRingoid, 0, 0);

        Bundle result = new Bundle();
        result.putInt(PARAM_SHOW_BACK, a.getBoolean(R.styleable.toolbarRingoid_is_show_back, false) ? View.VISIBLE : View.GONE);
        result.putString(PARAM_TITLE, a.getString(R.styleable.toolbarRingoid_toolbar_title));
        result.putString(PARAM_SUBTITLE, a.getString(R.styleable.toolbarRingoid_toolbar_subtitle));
        return result;
    }

    private void initViews(Bundle params) {
        findViewById(R.id.ivBack).setVisibility(getParamInt(params, PARAM_SHOW_BACK, View.GONE));

        String title = params.getString(PARAM_TITLE);
        String subtitle = params.getString(PARAM_SUBTITLE);

        ((TextView) findViewById(R.id.tvTitle)).setText(TextUtils.isEmpty(title) ? "" : title);
        ((TextView) findViewById(R.id.tvSubtitle)).setText(TextUtils.isEmpty(subtitle) ? "" : subtitle);
        findViewById(R.id.tvSubtitle).setVisibility(TextUtils.isEmpty(subtitle) ? GONE : VISIBLE);

    }

    private int getParamInt(Bundle params, String param, int defaultValue) {
        return params == null ? defaultValue : params.getInt(param, defaultValue);
    }
}
