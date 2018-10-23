package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.view.View;

import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;
import com.ringoid.view.ui.dialog.callback.ViewDialogsListener;

public interface IViewDialogs {

    void set(Context context, View view);

    void showDialogChatCompose(IDialogChatComposeListener listener);

    void showDialogMessage(int messageId);

    void showDialogErrorUnknown(IDialogErrorUnknownListener listenerDialogErrorUnknown);

    void setListener(ViewDialogsListener listener);
}
