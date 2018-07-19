package org.byters.ringoid.view.ui.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import org.byters.ringoid.view.ui.dialog.callback.IDialogDateCallback;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public class DialogDateBirth {

    private DialogListener listenerDialog;
    private DatePickerDialog dialog;
    private WeakReference<IDialogDateCallback> refCallback;

    public DialogDateBirth(Context context) {
        listenerDialog = new DialogListener();

        Calendar calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(context,
                listenerDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
    }

    public void setListener(IDialogDateCallback callback) {
        this.refCallback = new WeakReference<>(callback);
    }

    public void show() {
        if (dialog == null) return;
        dialog.cancel();
        dialog.show();
    }

    private class DialogListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (refCallback == null || refCallback.get() == null) return;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            refCallback.get().onResult(calendar.getTimeInMillis());
        }
    }
}
