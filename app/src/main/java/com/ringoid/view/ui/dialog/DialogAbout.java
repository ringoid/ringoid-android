package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ringoid.R;

public class DialogAbout implements View.OnClickListener {
    private AlertDialog dialog;

    public DialogAbout(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_about, null);
        view.findViewById(R.id.llContent).setOnClickListener(this);
        dialog.setView(view);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        cancel();
    }
}
