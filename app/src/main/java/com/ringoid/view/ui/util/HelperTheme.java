package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;

import java.lang.ref.WeakReference;

public class HelperTheme implements IHelperTheme {

    WeakReference<Context> refContext;

    public HelperTheme() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getColor(int res) {
        if (refContext == null || refContext.get() == null) return 0;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = refContext.get().getTheme();
        theme.resolveAttribute(R.attr.pageBackground, typedValue, true);
        return typedValue.data;
    }
}
