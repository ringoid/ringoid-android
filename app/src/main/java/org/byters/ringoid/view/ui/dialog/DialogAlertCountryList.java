package org.byters.ringoid.view.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import org.byters.ringoid.R;
import org.byters.ringoid.view.ui.adapter.AdapterCountryList;

public class DialogAlertCountryList {

    private AlertDialog dialog;

    public DialogAlertCountryList(Context context) {
        dialog = new AlertDialog.Builder(context)
                .create();

        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_country_list, null);

        dialog.setView(view);

        RecyclerView rvCountryList = view.findViewById(R.id.rvItems);
        rvCountryList.setLayoutManager(new LinearLayoutManager(context));
        rvCountryList.setAdapter(new AdapterCountryList());
    }

    public void cancel() {
        if (dialog == null) return;
        dialog.cancel();
    }

    public void show() {
        if (dialog == null) return;
        dialog.show();

    }
}
