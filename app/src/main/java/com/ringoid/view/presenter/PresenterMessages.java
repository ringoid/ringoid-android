/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.view.presenter.callback.IPresenterMessagesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterMessages implements IPresenterMessages {

    @Inject
    ICacheScroll cacheScroll;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private WeakReference<IPresenterMessagesListener> refListener;

    public PresenterMessages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }

    @Override
    public void onScrollState(int newState, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheScroll.onScrollIdle();
        cacheInterfaceState.setPositionScrollPageMessages(firstVisibleItemPosition);
    }

    @Override
    public void setListener(IPresenterMessagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {
        scrollToSavedPosition();
    }

    @Override
    public boolean isPositionTop() {
        if (refListener == null || refListener.get() == null) return false;
        return refListener.get().isPositionTop();
    }

    @Override
    public void scrollTop() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToTop();
    }

    private void scrollToSavedPosition() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToPosition(cacheInterfaceState.getPositionScrollPageMessages());
    }
}
