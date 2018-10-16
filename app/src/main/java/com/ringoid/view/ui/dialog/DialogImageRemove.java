package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogImageRemoveListener;

import java.lang.ref.WeakReference;

public class DialogImageRemove implements View.OnClickListener {
    private AlertDialog dialog;
    private String imageId;
    private WeakReference<IDialogImageRemoveListener> refListener;

    public DialogImageRemove(Context context, String imageId, IDialogImageRemoveListener listener) {
        this.refListener = new WeakReference<>(listener);
        this.imageId = imageId;

        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_image_remove, null);
        dialog.setView(view);

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
        if (v.getId() == R.id.tvConfirm) {
            notifySuccess();
        }
        cancel();
    }

    private void notifySuccess() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSuccess(imageId);
    }
}
