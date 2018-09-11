package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.ui.dialog.DialogLikeNoPhoto;
import com.ringoid.view.ui.dialog.callback.IDialogLIkeNoPhotoListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewDialogs implements IViewDialogs {

    @Inject
    INavigator navigator;

    private WeakReference<Context> refContext;
    private WeakReference<DialogLikeNoPhoto> refDialogLikeNoPhoto;
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

    private class ListenerDialogLikeNoPhoto implements IDialogLIkeNoPhotoListener {
        @Override
        public void onConfirm() {
            navigator.navigatePhotoAdd();
        }
    }
}
