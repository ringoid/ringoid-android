package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheScroll;

import javax.inject.Inject;

public class PresenterLikes implements IPresenterLikes {

    @Inject
    ICacheScroll cacheScroll;

    public PresenterLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }
}
