package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ringoid.R;

public class DialogMenuProfile implements View.OnClickListener {

    private AlertDialog dialog;

    public DialogMenuProfile(View view) {
        initDialog(view);
    }

    private void initDialog(View v) {
        dialog = new AlertDialog.Builder(v.getContext()).create();
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_dialog_profile, null);
        dialog.setView(view);

        view.findViewById(R.id.tvRemove).setOnClickListener(this);
        view.findViewById(R.id.flContent).setOnClickListener(this);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.y = (int) (v.getContext().getResources().getDisplayMetrics().density * (56 + 25));

        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
}
