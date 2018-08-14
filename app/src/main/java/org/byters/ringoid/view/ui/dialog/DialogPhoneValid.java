package org.byters.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.byters.ringoid.R;
import org.byters.ringoid.view.ui.dialog.callback.IDialogPhoneValidListener;

public class DialogPhoneValid implements DialogInterface.OnClickListener {
    private final AlertDialog dialog;
    private final IDialogPhoneValidListener listener;

    public DialogPhoneValid(Context context, String code, String phone, IDialogPhoneValidListener listener) {
        this.listener = listener;

        dialog = new AlertDialog.Builder(context)
                .setMessage(String.format(context.getResources().getString(R.string.message_dialog_phone_valid), code, phone))
                .setPositiveButton(R.string.dialog_phone_invalid_confirm, this)
                .setNegativeButton(R.string.dialog_phone_invalid_cancel, null)
                .create();

    }

    public void show() {
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(dialog.getContext().getResources().getColor(R.color.colorAccent));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(dialog.getContext().getResources().getColor(android.R.color.black));

    }

    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        listener.onConfirm();
    }
}
