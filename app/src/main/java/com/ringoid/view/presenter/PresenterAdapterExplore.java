/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterExplore implements IPresenterAdapterExplore {

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    IViewPopup viewPopup;

    public PresenterAdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
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
}
