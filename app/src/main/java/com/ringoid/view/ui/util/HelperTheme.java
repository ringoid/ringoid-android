package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.lang.ref.WeakReference;

public class HelperTheme implements IHelperTheme {

    WeakReference<Context> refContext;

    public void set(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Override
    public int getColor(int res) {
        if (refContext == null || refContext.get() == null) return 0;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = refContext.get().getTheme();
        theme.resolveAttribute(res, typedValue, true);
        return typedValue.data;
    }
}
