/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.ui.util.IHelperTheme;

import javax.inject.Inject;

public class DialogReport implements View.OnClickListener {

    @Inject
    IHelperTheme helperTheme;
    private TextView tvReport;
    private AlertDialog dialog;

    public DialogReport(Context context) {
        ApplicationRingoid.getComponent().inject(this);
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_report, null);
        dialog.setView(view);

        tvReport = view.findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this);
        view.findViewById(R.id.tvCancel).setOnClickListener(this);

        ((Switch) view.findViewById(R.id.swReport)).setOnCheckedChangeListener(new ListenerSwitch());
    }

    public void cancel() {
        dialog.cancel();
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        cancel();
    }

    private class ListenerSwitch implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            dialog.findViewById(R.id.rgReport).setVisibility(isChecked ? View.VISIBLE : View.GONE);
            tvReport.setTextColor(isChecked ? dialog.getContext().getResources().getColor(R.color.colorWarning)
                    : helperTheme.getColor(R.attr.colorTextPrimary));
            ((TextView) dialog.findViewById(R.id.tvReport)).setText(isChecked ? R.string.dialog_report_confirm_report : R.string.dialog_report_confirm);
        }
    }
}
