/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.ringoid.ApplicationRingoid;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewPopup implements IViewPopup {

    @Inject
    WeakReference<Context> refContext;

    WeakReference<View> refViewSnackbar;
    private Snackbar snackbar;

    public ViewPopup() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setView(View view) {
        this.refViewSnackbar = new WeakReference<>(view);
    }

    @Override
    public void showToast(int messageRes) {
        Context context = refContext.get();
        if (context == null) return;
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Context context = refContext.get();
        if (context == null) return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbar(int messageRes, int messageAction, View.OnClickListener listener) {
        View view = refViewSnackbar == null ? null : refViewSnackbar.get();
        if (view == null) return;

        if (snackbar != null) snackbar.dismiss();

        snackbar = Snackbar.make(view, messageRes, Snackbar.LENGTH_INDEFINITE).setAction(messageAction, listener);
        snackbar.show();
    }
}
