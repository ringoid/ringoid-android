/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ringoid.R;

public class DialogMenuImageOther implements View.OnClickListener {

    private AlertDialog dialog;
    private DialogBlock dialogBlock;
    private DialogReport dialogReport;

    public DialogMenuImageOther(View v) {
        dialogReport = new DialogReport(v.getContext());
        dialogBlock = new DialogBlock(v.getContext());

        initDialog(v);

    }

    private void initDialog(View v) {
        dialog = new AlertDialog.Builder(v.getContext()).create();
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_dialog_image_other, null);

        int[] pos = new int[2];
        v.getLocationOnScreen(pos);
        int yPos = pos[1];
        int margin = (int) (v.getContext().getResources().getDisplayMetrics().density * 12);
        int padding = (int) (v.getContext().getResources().getDisplayMetrics().density * 32);

        view.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        int h = view.getMeasuredHeight();
        int w = view.getMeasuredWidth();
        boolean isTop = yPos + padding < (v.getContext().getResources().getDisplayMetrics().heightPixels - h);
        view.findViewById(isTop ? R.id.ivTriangleTop : R.id.ivTriangleBottom).setVisibility(View.VISIBLE);

        dialog.setView(view, 0, 0, 0, 0);

        view.findViewById(R.id.tvReport).setOnClickListener(this);
        view.findViewById(R.id.tvBlock).setOnClickListener(this);
        view.findViewById(R.id.flContent).setOnClickListener(this);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.y = yPos - (isTop ? 0 : h + margin);
        lp.x = v.getContext().getResources().getDisplayMetrics().widthPixels - w;

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
        if (v.getId() == R.id.tvReport)
            dialogReport.show();

        if (v.getId() == R.id.tvBlock)
            dialogBlock.show();

        cancel();
    }
}
