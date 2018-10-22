package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;

public interface IViewDialogs {

    void set(Context context);

    void showDialogChatCompose(IDialogChatComposeListener listener);

    void showDialogMessage(int messageId);

    void showDialogErrorUnknown(IDialogErrorUnknownListener listenerDialogErrorUnknown);
}
