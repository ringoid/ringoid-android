/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache.listener;

public interface ICacheScrollListener {
    void onScroll(int dy, float alpha);

    void onScrollComplete(int scrollSum, int alpha, int scrollDirection);
}
