/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.presenter.callback.IPresenterExploreListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterExplore implements IPresenterExplore {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheTutorial cacheTutorial;

    private WeakReference<IPresenterExploreListener> refListener;

    public PresenterExplore() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }

    @Override
    public void onCreateView() {
        cacheTutorial.resetExplore();
    }

    @Override
    public void onScrollState(int newState) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheScroll.onScrollIdle();
    }

    @Override
    public boolean isPositionTop() {
        if (refListener == null || refListener.get() == null) return true;
        return refListener.get().isPositionTop();
    }

    @Override
    public void scrollTop() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollTop();
    }

    @Override
    public void setListener(IPresenterExploreListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
