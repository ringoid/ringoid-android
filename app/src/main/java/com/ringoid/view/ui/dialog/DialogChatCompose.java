package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.util.KeyboardUtils;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class DialogChatCompose implements View.OnClickListener {

    @Inject
    KeyboardUtils keyboardUtils;

    private View view;
    private EditText etMessage;
    private WeakReference<IDialogChatComposeListener> refListener;
    private BottomSheetDialog dialog;

    public DialogChatCompose(Context context, IDialogChatComposeListener listener) {
        ApplicationRingoid.getComponent().inject(this);
        this.refListener = new WeakReference<>(listener);
        dialog = new BottomSheetDialog(context);
        view = LayoutInflater.from(context).inflate(R.layout.view_dialog_chat_compose, null);
        dialog.setContentView(view);
        dialog.setOnShowListener(new ListenerDialogShow());
        dialog.setOnCancelListener(new ListenerCancel());

        etMessage = view.findViewById(R.id.etMessage);
        view.findViewById(R.id.flContent).setOnClickListener(this);
        view.findViewById(R.id.ivSend).setOnClickListener(this);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
        hideDim();
    }

    private void hideDim() {
        WindowManager.LayoutParams lp = DialogChatCompose.this.dialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
        DialogChatCompose.this.dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivSend) {
            if (TextUtils.isEmpty(etMessage.getText().toString().trim())) return;
            notifySend();
            cancel();
        }

        if (v.getId() == R.id.flContent) {
            cancel();
        }

    }

    private void notifySend() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSend(etMessage.getText().toString().trim());
    }

    private class ListenerDialogShow implements DialogInterface.OnShowListener {
        @Override
        public void onShow(DialogInterface dialog) {
            keyboardUtils.keyboardShow(DialogChatCompose.this.dialog.getContext(), etMessage);
        }
    }

    private class ListenerCancel implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialog) {
            keyboardUtils.keyboardHide(view.getContext(), view);
        }
    }
}
