/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogProfileLikesListener;
import com.ringoid.view.ui.dialog.callback.IDialogWhoLikedYouListener;

public class DialogWhoLikedYou implements View.OnClickListener {


    private IDialogWhoLikedYouListener listener;
    private AlertDialog dialog;
    private CheckBox cbDontShow;

    public DialogWhoLikedYou(Context context, IDialogWhoLikedYouListener listenerDialogProfileLikes) {
        this.listener = listenerDialogProfileLikes;
        initDialog(context);
    }

    private void initDialog(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_who_liked_you, null);
        dialog.setView(view);

        view.findViewById(R.id.tvAbout).setOnClickListener(this);
        view.findViewById(R.id.tvPush).setOnClickListener(this);
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
        if (v.getId() == R.id.tvAbout)
            listener.onSelectAbout(!cbDontShow.isChecked());

        if (v.getId() == R.id.tvPush)
            listener.onSelectPush(!cbDontShow.isChecked());

        if (v.getId() == R.id.tvOK)
            listener.onSelectOK(!cbDontShow.isChecked());

        cancel();
    }
}
