/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.view;

import android.content.Context;
import android.widget.Toast;

import com.ringoid.ApplicationRingoid;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewPopup implements IViewPopup {

    @Inject
    WeakReference<Context> refContext;

    public ViewPopup() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void showToast(int messageRes) {
        Context context = refContext.get();
        if (context == null) return;
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show();
    }
}
