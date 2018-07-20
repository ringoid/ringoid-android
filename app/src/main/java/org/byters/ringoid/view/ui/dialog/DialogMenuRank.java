package org.byters.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import org.byters.ringoid.R;

public class DialogMenuRank implements View.OnClickListener {

    private AlertDialog dialog;

    public DialogMenuRank(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_rank, null);
        dialog.setView(view);

        view.findViewById(R.id.tvLike).setOnClickListener(this);
        view.findViewById(R.id.tvSuperlike).setOnClickListener(this);
        view.findViewById(R.id.tvReport).setOnClickListener(this);
        view.findViewById(R.id.tvBlock).setOnClickListener(this);
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
