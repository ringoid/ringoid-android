package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.util.KeyboardUtils;
import com.ringoid.view.ui.view.EditTextPreIme;
import com.ringoid.view.ui.view.callback.IEditTextPreImeListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class DialogChatCompose2 implements View.OnClickListener {

    PopupWindow dialog;

    @Inject
    KeyboardUtils keyboardUtils;

    private WeakReference<IDialogChatComposeListener> refListener;
    private View viewContainer;

    private EditTextPreIme etMessage;

    public DialogChatCompose2(Context context, View container, IDialogChatComposeListener listener) {
        ApplicationRingoid.getComponent().inject(this);

        this.refListener = new WeakReference<>(listener);
        this.viewContainer = container;

        View popupView = LayoutInflater.from(context).inflate(R.layout.view_dialog_chat_compose, null);
        popupView.findViewById(R.id.ivSend).setOnClickListener(this);
        etMessage = popupView.findViewById(R.id.etMessage);
        etMessage.setListener(new ListenerViewPreIme());

        dialog = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        dialog.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        dialog.setAnimationStyle(0);

        dialog.setOutsideTouchable(true);
        dialog.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnDismissListener(new ListenerDismiss());
    }

    public void show() {
        if (dialog == null) return;
        dialog.showAtLocation(viewContainer, Gravity.BOTTOM, 0, 0);
    }

    public void cancel() {
        if (dialog == null) return;
        dialog.dismiss();
    }

    private void notifySend() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSend(etMessage.getText().toString().trim());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivSend) {
            if (!TextUtils.isEmpty(etMessage.getText().toString().trim()))
                notifySend();
            cancel();
        }
    }

    private class ListenerDismiss implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            keyboardUtils.keyboardHide(viewContainer.getContext(), viewContainer);
        }
    }

    private class ListenerViewPreIme implements IEditTextPreImeListener {

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dialog.dismiss();
            }

            return false;
        }
    }
}
