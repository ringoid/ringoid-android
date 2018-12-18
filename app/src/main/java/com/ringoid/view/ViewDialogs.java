package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.ui.dialog.DialogChatCompose;
import com.ringoid.view.ui.dialog.DialogErrorUnknown;
import com.ringoid.view.ui.dialog.DialogPhotoUploadedFirst;
import com.ringoid.view.ui.dialog.DialogReport;
import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;
import com.ringoid.view.ui.dialog.callback.IDialogPhotoUploadedFirstListener;
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
    private WeakReference<DialogPhotoUploadedFirst> refDialogPhotoUploadedFirst;
    private WeakReference<DialogReport> refDialogReport;

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

    @Override
    public void showDialogPhotoUploadedFirst(IDialogPhotoUploadedFirstListener listener) {
        if (refDialogPhotoUploadedFirst != null && refDialogPhotoUploadedFirst.get() != null)
            refDialogPhotoUploadedFirst.get().cancel();

        if (refContext == null || refContext.get() == null) return;
        DialogPhotoUploadedFirst dialog = new DialogPhotoUploadedFirst(refContext.get(), listener);
        dialog.show();
        refDialogPhotoUploadedFirst = new WeakReference<>(dialog);
    }

    @Override
    public void showDialogReport() {
        if (refDialogReport != null && refDialogReport.get() != null)
            refDialogReport.get().cancel();

        if (refContext == null || refContext.get() == null) {
            return;
        }
        DialogReport dialogReport = new DialogReport(refContext.get());
        dialogReport.show();
        refDialogReport = new WeakReference<>(dialogReport);
    }

    private class DialogErrorUnknownListener implements IDialogErrorUnknownListener {
        @Override
        public void onDismiss() {

        }

        @Override
        public void onConfirm() {
            if (refListener == null || refListener.get() == null) {
                return;
            }
            refListener.get().onDialogErrorConfirm();
        }
    }
}
