package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {

    public void keyboardShow(Context context, EditText editView) {
        if (context == null || editView == null) return;
        editView.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(editView, InputMethodManager.SHOW_FORCED);
    }

    public void keyboardHide(Context context, View view) {
        if (context == null || view == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void keyboardShow(Window window) {
        if (window == null) return;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
