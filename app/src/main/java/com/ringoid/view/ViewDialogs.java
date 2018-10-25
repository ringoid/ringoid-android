package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.ui.dialog.DialogChatCompose;
import com.ringoid.view.ui.dialog.DialogErrorUnknown;
import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;
import com.ringoid.view.ui.dialog.callback.ViewDialogsListener;
import com.ringoid.view.ui.util.IHelperFullscreen;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewDialogs implements IViewDialogs {


    @Inject
    IHelperFullscreen helperFullscreen;

    private WeakReference<Context> refContext;
    private WeakReference<View> refView;

    private WeakReference<DialogChatCompose> refDialogChatCompose;
    private WeakReference<DialogErrorUnknown> refDialogErrorUnknown;
    private WeakReference<AlertDialog> refDialogMessage;

    private DialogErrorUnknownListener dialogErrorUnknownListener;
    private WeakReference<ViewDialogsListener> refListener;

    public ViewDialogs() {
        ApplicationRingoid.getComponent().inject(this);
        dialogErrorUnknownListener = new DialogErrorUnknownListener();
    }

    @Override
    public void set(Context context, View view) {
        this.refContext = new WeakReference<>(context);
        this.refView = new WeakReference<>(view);
    }

    @Override
    public void showDialogChatCompose() {

        if (refDialogChatCompose != null && refDialogChatCompose.get() != null)
            refDialogChatCompose.get().cancel();

        if (refContext == null || refContext.get() == null) return;

        DialogChatCompose dialogChatCompose = new DialogChatCompose(refContext.get(), refView.get());
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
    public void showDialogErrorUnknown(IDialogErrorUnknownListener listenerDialogErrorUnknown) {
        if (refDialogErrorUnknown != null && refDialogErrorUnknown.get() != null)
            refDialogErrorUnknown.get().cancel();

        if (refContext == null || refContext.get() == null) return;
        DialogErrorUnknown dialog = new DialogErrorUnknown(refContext.get(), dialogErrorUnknownListener, listenerDialogErrorUnknown);
        dialog.show();
        refDialogErrorUnknown = new WeakReference<>(dialog);
    }

    @Override
    public void setListener(ViewDialogsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private class DialogErrorUnknownListener implements IDialogErrorUnknownListener {
        @Override
        public void onDismiss() {

        }

        @Override
        public void onConfirm() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onDialogErrorConfirm();
        }
    }
}
