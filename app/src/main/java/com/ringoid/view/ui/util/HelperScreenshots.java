package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class HelperScreenshots implements IHelperScreenshots {

    private boolean debugSessionScreenshotsEnabled;
    private WeakReference<Window> refWindow;


    public HelperScreenshots() {
        debugSessionScreenshotsEnabled = false;
    }

    @Override
    public void enableScreenshots() {
        if (refWindow == null || refWindow.get() == null) return;
        refWindow.get().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void disableScreenshots() {
        if (debugSessionScreenshotsEnabled) return;
        if (refWindow == null || refWindow.get() == null) return;
        refWindow.get().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void changeStateScreenshots() {
        debugSessionScreenshotsEnabled = !debugSessionScreenshotsEnabled;
    }

    @Override
    public boolean isScreenshotsSecured() {
        if (refWindow == null || refWindow.get() == null) return false;
        return (refWindow.get().getAttributes().flags & WindowManager.LayoutParams.FLAG_SECURE) != 0;
    }

    @Override
    public void set(Window window) {
        this.refWindow = new WeakReference<>(window);
    }

    @Override
    public boolean isScreenshotsDebugEnabled() {
        return debugSessionScreenshotsEnabled;
    }
}
