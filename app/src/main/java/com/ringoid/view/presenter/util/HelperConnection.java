package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ringoid.ApplicationRingoid;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class HelperConnection implements IHelperConnection {

    @Inject
    WeakReference<Context> refContext;

    public HelperConnection() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public boolean isConnectionExist() {
        if (refContext == null || refContext.get() == null) return false;

        ConnectivityManager cm = (ConnectivityManager) refContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
