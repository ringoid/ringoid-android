package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.ui.dialog.DialogChatCompose;
import com.ringoid.view.ui.dialog.DialogErrorUnknown;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.util.IHelperFullscreen;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewDialogs implements IViewDialogs {

    @Inject
    INavigator navigator;

    @Inject
    IHelperFullscreen helperFullscreen;

    private WeakReference<Context> refContext;

    private WeakReference<DialogChatCompose> refDialogChatCompose;
    private WeakReference<DialogErrorUnknown> refDialogErrorUnknown;
    private WeakReference<AlertDialog> refDialogMessage;

    public ViewDialogs() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void set(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Override
    public void showDialogChatCompose(IDialogChatComposeListener listener) {

        if (refDialogChatCompose != null && refDialogChatCompose.get() != null)
            refDialogChatCompose.get().cancel();

        if (refContext == null || refContext.get() == null) return;

        DialogChatCompose dialogChatCompose = new DialogChatCompose(refContext.get(), listener);
        dialogChatCompose.show();
        refDialogChatCompose = new WeakReference<>(dialogChatCompose);
    }

    @Override
    public void showDialogMessage(int messageId) {
        if (refDialogMessage != null && refDialogMessage.get() != null)
            refDialogMessage.get().cancel();

        if (refContext == null || refContext.get() == null) return;
        AlertDialog dialog = new AlertDialog.Builder(refContext.get())
                .setMessage(messageId)
                .create();
        dialog.show();
        refDialogMessage = new WeakReference<>(dialog);
    }

    @Override
    public void showDialogErrorUnknown() {
        if (refDialogErrorUnknown != null && refDialogErrorUnknown.get() != null)
            refDialogErrorUnknown.get().cancel();

        if (refContext == null || refContext.get() == null) return;
        DialogErrorUnknown dialog = new DialogErrorUnknown(refContext.get());
        dialog.show();
        refDialogErrorUnknown = new WeakReference<>(dialog);
    }

}
