package com.ringoid.view.ui.dialog;

import android.view.View;

import com.ringoid.R;

public class DialogMenuChat extends DialogMenuImageOther {

    private DialogReportChat dialogReport;

    public DialogMenuChat(View v) {
        super(v);
        dialogReport = new DialogReportChat(v.getContext());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvReport) {
            dialogReport.show();
            cancel();
            return;
        }

        super.onClick(v);
    }
}
