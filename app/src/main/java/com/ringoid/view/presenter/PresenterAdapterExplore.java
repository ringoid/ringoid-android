/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.listener.ICacheExploreListener;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterAdapterExploreListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterExplore implements IPresenterAdapterExplore {

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    IViewPopup viewPopup;

    private ICacheExploreListener listenerCacheExplore;
    private WeakReference<IPresenterAdapterExploreListener> refListener;

    public PresenterAdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
        cacheExplore.addListener(listenerCacheExplore = new ListenerCacheExplore());
    }

    @Override
    public int getItemsNum() {
        return cacheExplore.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheExplore.getItemsNum(position);
    }

    @Override
    public void onClickScrolls() {

    }

    @Override
    public void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheExplore.setSelected(adapterPosition, firstVisibleItemPosition);
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return cacheExplore.getSelectedPhotoPosition(position);
    }

    @Override
    public void setListener(IPresenterAdapterExploreListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private class ListenerCacheExplore implements ICacheExploreListener {
        @Override
        public void onUpdate() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }
}
