package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.CountDownTimer;

import com.ringoid.view.ui.util.listener.IHelperTimerListener;

import java.lang.ref.WeakReference;

public class HelperTimer implements IHelperTimer {

    private static final int INTERVAL_MILLIS = 1000;
    private static final int TIME_MILLIS = 81000;
    private WeakReference<IHelperTimerListener> refListener;
    private Timer timer;

    @Override
    public void start() {
        if (timer != null) timer.cancel();
        timer = new Timer(TIME_MILLIS, INTERVAL_MILLIS);
        timer.start();
    }

    @Override
    public void setListener(IHelperTimerListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void cancel() {
        if (timer == null) return;
        timer.cancel();
        timer = null;
        notifyFinish();
    }

    @Override
    public boolean isTicking() {
        return timer != null && timer.isTicking();
    }

    @Override
    public long getMillis() {
        return timer == null ? 0 : timer.millis;
    }

    private void notifyFinish() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onFinish();
    }

    private class Timer extends CountDownTimer {

        private boolean isTicking;
        private long millis;

        Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            isTicking = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            this.millis = millisUntilFinished;
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onTick(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            this.millis = 0;
            isTicking = false;
            notifyFinish();
        }

        boolean isTicking() {
            return isTicking;
        }
    }
}
