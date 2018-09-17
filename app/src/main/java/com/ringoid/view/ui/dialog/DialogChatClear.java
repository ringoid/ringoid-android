package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogChatClearListener;

import java.lang.ref.WeakReference;

public class DialogChatClear implements View.OnClickListener {

    private WeakReference<IDialogChatClearListener> refListener;
    private AlertDialog dialog;


    public DialogChatClear(Context context, IDialogChatClearListener listener) {
        this.refListener = new WeakReference<>(listener);
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_chat_clear, null);
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
        if (v.getId() == R.id.tvConfirm)
            notifyListener();
        cancel();
    }

    private void notifyListener() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onConfirm();
    }

}
