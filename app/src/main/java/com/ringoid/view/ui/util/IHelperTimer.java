package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.ui.util.listener.IHelperTimerListener;

public interface IHelperTimer {
    void start();

    void setListener(IHelperTimerListener listener);

    void cancel();

    boolean isTicking();

    long getMillis();
}
