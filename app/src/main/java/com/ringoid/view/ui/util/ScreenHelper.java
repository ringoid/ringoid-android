package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.ApplicationRingoid;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ScreenHelper implements IScreenHelper {

    @Inject
    WeakReference<Context> refContext;

    public ScreenHelper() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public String getImageRatioString() {
        if (refContext == null || refContext.get() == null)
            return "480x640";

        int w = refContext.get().getResources().getDisplayMetrics().widthPixels;

        return String.valueOf(w + "x" + (int) (w / 3f * 4));
    }
}
