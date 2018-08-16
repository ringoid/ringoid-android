package com.ringoid.controller.data.memorycache;

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.listener.ICacheScrollListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class CacheScroll implements ICacheScroll {

    private final int maxScroll;
    @Inject
    WeakReference<Context> refContext;

    private WeakReference<ICacheScrollListener> refListener;
    private int scrollSum;

    public CacheScroll() {
        ApplicationRingoid.getComponent().inject(this);

        resetCache();

        maxScroll = (int) refContext.get().getResources().getDimension(R.dimen.toolbar_height);
    }

    @Override
    public void resetCache() {
        scrollSum = 0;
        notifyListeners();
    }

    private void notifyListeners() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onScroll(scrollSum, 1 - scrollSum / (float) maxScroll);
    }

    @Override
    public void onScroll(int dy) {

        scrollSum += dy;

        scrollSum = scrollSum >= 0
                ? Math.min(scrollSum, maxScroll)
                : 0;

        notifyListeners();
    }

    @Override
    public void setListener(ICacheScrollListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
