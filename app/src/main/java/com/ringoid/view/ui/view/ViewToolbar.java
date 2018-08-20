package com.ringoid.view.ui.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ringoid.R;

public class ViewToolbar extends FrameLayout {

    private static final String PARAM_SHOW_BACK = "param_show_back";

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
        return result;
    }

    private void initViews(Bundle params) {
        findViewById(R.id.ivBack).setVisibility(getParamInt(params, PARAM_SHOW_BACK, View.GONE));
    }

    private int getParamInt(Bundle params, String param, int defaultValue) {
        return params == null ? defaultValue : params.getInt(param, defaultValue);
    }
}
