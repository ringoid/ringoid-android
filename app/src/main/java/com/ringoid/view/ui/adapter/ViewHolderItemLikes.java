/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemLikes extends ViewHolderItemLikeBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private AdapterLikesImages adapter;

    ViewHolderItemLikes(ViewGroup parent) {
        super(parent, R.layout.view_item_images_likes);
        ApplicationRingoid.getComponent().inject(this);
        initList();
    }

    @Override
    protected void initList() {
        adapter = new AdapterLikesImages();
        rvItems.setAdapter(adapter);
        rvItems.addOnScrollListener(new ListenerScrollPhotos());
        dotsIndicatorHelper = IndicatorHelper.getLinesPinkHelper(flDots, rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
    }

    @Override
    protected boolean isChatEmpty(int position) {
        return presenterAdapterLikes.isChatEmpty(position);
    }

    @Override
    protected boolean isLikedAnyPhoto(int position) {
        return presenterAdapterLikes.isLikedAnyPhoto(position);
    }

    @Override
    protected void onClickChat() {
        presenterAdapterLikes.onClickChat(getAdapterPosition() - 1);
    }

    @Override
    void setData(int position) {
        super.setData(position);
        adapter.setPosition(position);
        rvItems.scrollToPosition(presenterAdapterLikes.getSelectedPhotoPosition(position));
        dotsIndicatorHelper.updateData(presenterAdapterLikes.getItemsNum(position));
    }

    @Override
    protected boolean isControlsVisible() {
        return presenterAdapterLikes.isControlsVisible();
    }

    private class ListenerScrollPhotos extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            presenterAdapterLikes.onScrollPhotoChanged(newState, getAdapterPosition() - 1, layoutManager.findFirstVisibleItemPosition());
        }
    }
}
