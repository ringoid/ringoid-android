package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterDialogErrorUnknown;
import com.ringoid.view.presenter.callback.IPresenterDialogErrorUnknownListener;

import javax.inject.Inject;

public class DialogErrorUnknown {

    @Inject
    IPresenterDialogErrorUnknown presenterDialogErrorNetwork;

    private AlertDialog dialog;
    private ListenerPresenter listenerPresenter;
    private TextView tvMessage;

    public DialogErrorUnknown(Context context) {
        ApplicationRingoid.getComponent().inject(this);
        presenterDialogErrorNetwork.setListener(listenerPresenter = new ListenerPresenter());

        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_error_unknown, null);
        dialog.setView(view);

        tvMessage = view.findViewById(R.id.tvMessage);
    }

    public void show() {
        dialog.show();
        presenterDialogErrorNetwork.onShow(dialog.getContext());
    }

    public void cancel() {
        dialog.cancel();
    }

    private class ListenerPresenter implements IPresenterDialogErrorUnknownListener {

        @Override
        public void setMessage(String message) {
            tvMessage.setText(String.format(dialog.getContext().getResources().getString(R.string.message_error_unkown_format), message));
        }
    }
}
