package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.Window;
import android.view.WindowManager;

import com.ringoid.BuildConfig;

public class HelperScreenshots implements IHelperScreenshots {

    public HelperScreenshots() {
    }

    @Override
    public void init(Window window) {
        window.setFlags(BuildConfig.DEBUG ? 0 : WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void changeStateScreenshots(Window window) {
        window.setFlags(isScreenshotsSecured(window) ? 0 : WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public boolean isScreenshotsSecured(Window window) {
        return (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_SECURE) != 0;
    }
}
