package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Build;
import android.view.View;
import android.view.Window;

import java.lang.ref.WeakReference;

public class HelperFullscreen implements IHelperFullscreen {

    private WeakReference<Window> refWindow;

    @Override
    public void set(Window window) {
        this.refWindow = new WeakReference<>(window);
    }

    @Override
    public void statusbarShowFullscreen() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;

        if (refWindow == null || refWindow.get() == null) return;
        View decorView = refWindow.get().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void statusbarHide() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (refWindow == null || refWindow.get() == null) return;
        View decorView = refWindow.get().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void statusbarShowResizeable() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (refWindow == null || refWindow.get() == null) return;
        refWindow.get().getDecorView().setSystemUiVisibility(0);
    }
}
