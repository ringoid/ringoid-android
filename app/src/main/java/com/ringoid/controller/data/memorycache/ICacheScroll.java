package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheScrollListener;

public interface ICacheScroll {
    void resetCache();

    void onScroll(int dy);

    void setListener(ICacheScrollListener listener);
}
