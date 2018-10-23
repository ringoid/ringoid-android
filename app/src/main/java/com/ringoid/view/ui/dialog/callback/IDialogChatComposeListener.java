package com.ringoid.view.ui.dialog.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IDialogChatComposeListener {
    void onSend(String message);

    void onDismiss(String message);
}
