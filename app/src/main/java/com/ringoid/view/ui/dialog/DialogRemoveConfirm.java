package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ringoid.R;
import com.ringoid.model.DataBlacklistPhone;
import com.ringoid.view.ui.dialog.callback.IDialogRemoveConfirmListener;

public class DialogRemoveConfirm implements View.OnClickListener {

    private DataBlacklistPhone phone;
    private IDialogRemoveConfirmListener listener;
    private AlertDialog dialog;

    public DialogRemoveConfirm(Context context, DataBlacklistPhone phone, IDialogRemoveConfirmListener listener) {
        this.listener = listener;
        this.phone=phone;
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_remove_confirm, null);
        dialog.setView(view);

        ((TextView) view.findViewById(R.id.tvMessage)).setText(String.format(context.getString(R.string.format_dialog_remove_confirm), phone.getCode(), phone.getPhone()));
        view.findViewById(R.id.tvConfirm).setOnClickListener(this);
        view.findViewById(R.id.tvCancel).setOnClickListener(this);
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
            listener.onConfirm(phone);
        cancel();
    }
}
