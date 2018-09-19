package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Handler;

public class HelperThreadMain implements IHelperThreadMain {
    private Handler handler;

    public HelperThreadMain(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
