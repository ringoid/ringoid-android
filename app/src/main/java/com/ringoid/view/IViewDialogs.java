package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.view.View;

import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;
import com.ringoid.view.ui.dialog.callback.IDialogPhotoUploadedFirstListener;
import com.ringoid.view.ui.dialog.callback.ViewDialogsListener;

public interface IViewDialogs {

    void set(Context context, View view);

    void showDialogChatCompose();

    void showDialogMessage(int messageId);

    void showDialogErrorUnknown(IDialogErrorUnknownListener listenerDialogErrorUnknown);

    void setListener(ViewDialogsListener listener);

    void showDialogPhotoUploadedFirst(IDialogPhotoUploadedFirstListener listenerDialogPhotoUpload);

    void showDialogReport();
}
