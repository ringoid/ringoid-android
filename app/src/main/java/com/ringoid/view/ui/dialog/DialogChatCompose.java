package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;

import java.lang.ref.WeakReference;

public class DialogChatCompose implements View.OnClickListener {

    private TextView etMessage;
    private WeakReference<IDialogChatComposeListener> refListener;
    private BottomSheetDialog dialog;

    public DialogChatCompose(Context context, IDialogChatComposeListener listener) {
        this.refListener = new WeakReference<>(listener);
        dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_chat_compose, null);
        dialog.setContentView(view);

        etMessage = view.findViewById(R.id.etMessage);
        view.findViewById(R.id.flContent).setOnClickListener(this);
        view.findViewById(R.id.ivSend).setOnClickListener(this);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivSend) {
            notifySend();
            cancel();
        }

        if (v.getId() == R.id.flContent) {
            cancel();
        }

    }

    private void notifySend() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSend(etMessage.getText().toString());
    }
}
