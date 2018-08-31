package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.ringoid.R;

public class StatusBarViewHelper implements IStatusBarViewHelper {
    @Override
    public void setColor(AppCompatActivity activity, int type) {
        if (activity == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(type == 0
                    ? android.R.color.black
                    : type == 1
                    ? R.color.colorPrimaryDark
                    : R.color.colorAccent));
        }
    }
}
