/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogHiddenModeListener;

public class DialogHiddenMode implements View.OnClickListener {


    private IDialogHiddenModeListener listener;
    private AlertDialog dialog;
    private CheckBox cbDontShow;

    public DialogHiddenMode(Context context, IDialogHiddenModeListener listenerDialogProfileLikes) {
        this.listener = listenerDialogProfileLikes;
        initDialog(context);
    }

    private void initDialog(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_hidden_mode, null);
        dialog.setView(view);

        view.findViewById(R.id.tvSettings).setOnClickListener(this);
        view.findViewById(R.id.tvOK).setOnClickListener(this);
        cbDontShow = view.findViewById(R.id.cbShowAgain);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tvSettings)
            listener.onSelectSettings(!cbDontShow.isChecked());

        if (v.getId() == R.id.tvOK)
            listener.onSelectOK(!cbDontShow.isChecked());

        cancel();
    }
}
