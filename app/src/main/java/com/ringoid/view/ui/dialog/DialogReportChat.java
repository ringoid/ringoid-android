package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ringoid.R;

public class DialogReportChat implements View.OnClickListener{

    private AlertDialog dialog;

    public DialogReportChat(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_report_chat, null);
        dialog.setView(view);

        view.findViewById(R.id.tvReport).setOnClickListener(this);
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
        cancel();
    }
}
