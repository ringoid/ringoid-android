package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.model.IMAGE_SUPPORTED;

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
            return IMAGE_SUPPORTED.x1.getValue();

        int w = refContext.get().getResources().getDisplayMetrics().widthPixels;

        IMAGE_SUPPORTED item = IMAGE_SUPPORTED.getClosestWidthUp(w);
        return item.getValue();
    }
}
