/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogPrivacyPhotosListener;

public class DialogPrivacyPhotos implements View.OnClickListener {


    private IDialogPrivacyPhotosListener listener;
    private AlertDialog dialog;

    public DialogPrivacyPhotos(Context context, int selected, IDialogPrivacyPhotosListener listenerDialogProfileLikes) {
        this.listener = listenerDialogProfileLikes;
        initDialog(context, selected);
    }

    private void initDialog(Context context, int selected) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_privacy_photos, null);
        dialog.setView(view);

        ((RadioButton) view.findViewById(selected == 0 ? R.id.rbAll
                : selected == 1 ? R.id.rbLiked
                : R.id.rbNoone)).setChecked(true);

        view.findViewById(R.id.rbAll).setOnClickListener(this);
        view.findViewById(R.id.rbLiked).setOnClickListener(this);
        view.findViewById(R.id.rbNoone).setOnClickListener(this);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rbAll)
            listener.onClickAll();

        if (v.getId() == R.id.rbLiked)
            listener.onClickLiked();

        if (v.getId() == R.id.rbNoone)
            listener.onClickNoone();

        cancel();
    }
}
