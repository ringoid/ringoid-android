/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ringoid.R;

public class ViewPrivacyItemDescription extends FrameLayout {
    private static final String PARAM_TITLE_RES = "param_title";
    private static final String PARAM_SUBTITLE_RES = "param_subtitle";

    public ViewPrivacyItemDescription(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ViewPrivacyItemDescription(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public ViewPrivacyItemDescription(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_privacy_item_description, this);
        Bundle params = initAttrs(context, attrs);
        initViews(params);
    }

    private Bundle initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return null;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.viewPrivacyItemRingoid, 0, 0);

        Bundle result = new Bundle();
        result.putString(PARAM_TITLE_RES, a.getString(R.styleable.viewPrivacyItemRingoid_item_privacy_title));
        result.putString(PARAM_SUBTITLE_RES, a.getString(R.styleable.viewPrivacyItemRingoid_item_privacy_subtitle));
        return result;
    }

    private void initViews(Bundle params) {

        ((TextView) findViewById(R.id.tvTitle)).setText(getParamString(params, PARAM_TITLE_RES, ""));
        ((TextView) findViewById(R.id.tvSubtitle)).setText(getParamString(params, PARAM_SUBTITLE_RES, ""));

        findViewById(R.id.llItem).setOnClickListener(new ListenerClick());
    }

    private String getParamString(Bundle params, String param, String defaultValue) {
        return params == null || TextUtils.isEmpty(params.getString(param))
                ? defaultValue
                : params.getString(param, defaultValue);
    }

    private class ListenerClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            //Toast.makeText(v.getContext(), R.string.settings_privacy_description_click_message, Toast.LENGTH_SHORT).show();
        }
    }
}
