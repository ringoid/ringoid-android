/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.listener.ICacheScrollListener;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheScroll implements ICacheScroll {

    public static final int SCROLL_UP = 1;
    public static final int SCROLL_DOWN = 2;

    private final int maxScroll;

    @Inject
    WeakReference<Context> refContext;

    private int scrollSum;
    private int scrollDirection;
    private int position;
    private WeakHashMap<String, ICacheScrollListener> listeners;

    public CacheScroll() {
        ApplicationRingoid.getComponent().inject(this);

        resetCache();

        maxScroll = (int) refContext.get().getResources().getDimension(R.dimen.toolbar_height_with_statusbar);
    }

    @Override
    public void resetCache() {
        scrollSum = 0;
        notifyListeners();
    }


    @Override
    public void onScroll(int dy, int scrollSum) {
        scrollDirection = dy > 0 ? SCROLL_DOWN : SCROLL_UP;

        this.scrollSum = scrollSum;

        position = position == 0 && scrollDirection == SCROLL_UP ? 0 : -scrollSum;
        if (scrollSum > maxScroll)
            position = scrollDirection == SCROLL_DOWN ? -maxScroll : 0;

        notifyListeners();
    }

    @Override
    public void onScrollIdle() {
        notifyListenersCompleteScroll();
    }

    @Override
    public void addListener(ICacheScrollListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    private void notifyListenersCompleteScroll() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheScrollListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onScrollComplete(scrollSum, maxScroll, scrollDirection == SCROLL_DOWN);
        }
    }

    private void notifyListeners() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheScrollListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onScroll(scrollDirection == SCROLL_DOWN, position);
        }
    }
}
