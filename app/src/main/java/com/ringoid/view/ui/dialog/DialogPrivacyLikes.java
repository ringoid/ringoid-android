package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.callback.IDialogPrivacyLikesListener;

import java.lang.ref.WeakReference;

public class DialogPrivacyLikes implements View.OnClickListener {

    private WeakReference<IDialogPrivacyLikesListener> refListener;
    private AlertDialog dialog;

    public DialogPrivacyLikes(Context context, IDialogPrivacyLikesListener listener) {
        this.refListener = new WeakReference<>(listener);
        initDialog(context);
    }

    private void initDialog(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_privacy_likes, null);
        dialog.setView(view);

        view.findViewById(R.id.rbMatches).setOnClickListener(this);
        view.findViewById(R.id.rbLiked).setOnClickListener(this);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rbMatches)
            if (refListener != null && refListener.get() != null) refListener.get().onClickMatches();

        if (v.getId() == R.id.rbLiked)
            if (refListener != null && refListener.get() != null) refListener.get().onClickLiked();

        cancel();
    }
}
