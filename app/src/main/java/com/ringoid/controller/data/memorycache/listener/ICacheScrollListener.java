/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache.listener;

public interface ICacheScrollListener {
    void onScroll(boolean isDown, int scrollSum);

    void onScrollComplete(int scrollSum, int maxScroll, boolean b);
}
