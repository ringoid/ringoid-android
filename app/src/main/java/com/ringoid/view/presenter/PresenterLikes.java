/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheTutorial;

import javax.inject.Inject;

public class PresenterLikes implements IPresenterLikes {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheTutorial cacheTutorial;

    public PresenterLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }

    @Override
    public void onCreateView() {
        cacheTutorial.resetLikesNum();
    }

    @Override
    public void onScrollState(int newState) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheScroll.onScrollIdle();
    }
}
