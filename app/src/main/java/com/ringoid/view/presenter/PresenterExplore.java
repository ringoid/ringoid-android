package com.ringoid.view.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class PresenterExplore implements IPresenterExplore {

    @Inject
    ICacheScroll cacheScroll;

    public PresenterExplore() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }
}
