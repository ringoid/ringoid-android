/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogErrorAppVersionListener;
import com.ringoid.view.ui.dialog.callback.IDialogLogoutListener;

public class DialogErrorAppVersion implements View.OnClickListener {

    private IDialogErrorAppVersionListener listener;
    private AlertDialog dialog;

    public DialogErrorAppVersion(Context context, IDialogErrorAppVersionListener listener) {
        this.listener = listener;
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_error_app_version, null);
        dialog.setView(view);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvConfirm)
            listener.onConfirm();
        cancel();
    }
}
