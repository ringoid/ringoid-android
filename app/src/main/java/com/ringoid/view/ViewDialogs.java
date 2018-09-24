package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.ui.dialog.DialogChatCompose;
import com.ringoid.view.ui.dialog.DialogLikeNoPhoto;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.dialog.callback.IDialogLIkeNoPhotoListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewDialogs implements IViewDialogs {

    @Inject
    INavigator navigator;

    private WeakReference<Context> refContext;

    private WeakReference<DialogLikeNoPhoto> refDialogLikeNoPhoto;
    private WeakReference<DialogChatCompose> refDialogChatCompose;
    private WeakReference<AlertDialog> refDialogMessage;

    private IDialogLIkeNoPhotoListener listenerDialogLikeNoPhoto;

    public ViewDialogs() {
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogLikeNoPhoto = new ListenerDialogLikeNoPhoto();
    }

    @Override
    public void showDialogExplore() {
    }

    @Override
    public void set(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Override
    public void showDialogLikes() {

    }

    @Override
    public void showDialogLikeNoPhoto() {
        if (refDialogLikeNoPhoto != null && refDialogLikeNoPhoto.get() != null)
            refDialogLikeNoPhoto.get().cancel();

        if (refContext == null || refContext.get() == null) return;
        DialogLikeNoPhoto dialogLikeNoPhoto = new DialogLikeNoPhoto(refContext.get(), listenerDialogLikeNoPhoto);
        dialogLikeNoPhoto.show();
        refDialogLikeNoPhoto = new WeakReference<>(dialogLikeNoPhoto);

    }

    @Override
    public void showDialogChatCompose(IDialogChatComposeListener listener) {

        if (refDialogChatCompose != null && refDialogChatCompose.get() != null)
            refDialogChatCompose.get().cancel();

        if (refContext == null || refContext.get() == null) return;

        navigator.statusbarShow();
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

    private class ListenerDialogLikeNoPhoto implements IDialogLIkeNoPhotoListener {
        @Override
        public void onConfirm() {
            navigator.navigatePhotoAdd();
        }
    }
}
