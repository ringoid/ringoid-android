package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogPhotoUploadedFirstListener;

import java.lang.ref.WeakReference;

public class DialogPhotoUploadedFirst implements View.OnClickListener {

    private WeakReference<IDialogPhotoUploadedFirstListener> refListener;
    private AlertDialog dialog;

    public DialogPhotoUploadedFirst(Context context, IDialogPhotoUploadedFirstListener listener) {

        this.refListener = new WeakReference<>(listener);

        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_photo_uploaded_first, null);

        view.findViewById(R.id.tvFeed).setOnClickListener(this);
        view.findViewById(R.id.tvPhotoAdd).setOnClickListener(this);
        dialog.setView(view);
    }

    public void show() {
        dialog.show();
    }

    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvFeed)
            notifyListenersFeed();
        if (v.getId() == R.id.tvPhotoAdd)
            notifyListenersPhotoAdd();
        cancel();
    }

    private void notifyListenersFeed() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSelectFeed();
    }

    private void notifyListenersPhotoAdd() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onSelectUploadPhoto();
    }

}
