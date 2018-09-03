/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ringoid.R;

public class DialogReport implements View.OnClickListener {

    private AlertDialog dialog;

    public DialogReport(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_report, null);
        dialog.setView(view);

        view.findViewById(R.id.tvReport).setOnClickListener(this);
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
            dialog.findViewById(R.id.tvReportMessageChat).setVisibility(isChecked ? View.VISIBLE : View.GONE);

            ((TextView) dialog.findViewById(R.id.tvReport)).setText(isChecked ? R.string.dialog_report_confirm_report : R.string.dialog_report_confirm);
            ((TextView) dialog.findViewById(R.id.tvReport)).setTextColor(ContextCompat.getColor(dialog.getContext(), isChecked ? R.color.colorAccent : android.R.color.black));
        }
    }
}
