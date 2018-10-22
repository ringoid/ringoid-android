package com.ringoid.view.ui.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ringoid.R;

public class ViewRange extends FrameLayout {
    private static final String PARAM_TITLE_RES = "title";
    private static final String PARAM_VALUE_RES = "value";

    public ViewRange(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ViewRange(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ViewRange(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_range, this);
        Bundle params = initAttrs(context, attrs);
        initViews(params);

    }

    private void initViews(Bundle params) {
        if (params==null) return;
        ((TextView)findViewById(R.id.tvTitle)).setText(getParamString(params, PARAM_TITLE_RES, ""));
        ((TextView)findViewById(R.id.tvValue)).setText(getParamString(params, PARAM_VALUE_RES, ""));
    }

    private String getParamString(Bundle params, String param, String defaultValue) {
        return params == null || TextUtils.isEmpty(params.getString(param))
                ? defaultValue
                : params.getString(param, defaultValue);
    }

    private Bundle initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return null;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.viewRange, 0, 0);

        Bundle result = new Bundle();
        result.putString(PARAM_TITLE_RES, a.getString(R.styleable.viewRange_title));
        result.putString(PARAM_VALUE_RES, a.getString(R.styleable.viewRange_value));
        return result;
    }


}
