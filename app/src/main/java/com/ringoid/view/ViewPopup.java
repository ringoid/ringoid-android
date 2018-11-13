/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/


package com.ringoid.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewPopup implements IViewPopup {

    @Inject
    WeakReference<Context> refContext;


    public ViewPopup() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void showToast(int messageRes) {
        Context context = refContext.get();
        if (context == null) return;
        Toast toast = Toast.makeText(context, messageRes, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        View view = LayoutInflater.from(refContext.get()).inflate(R.layout.view_toast, null);
        ((TextView) view.findViewById(android.R.id.message)).setText(messageRes);
        toast.setView(view);

        toast.show();
    }
}
