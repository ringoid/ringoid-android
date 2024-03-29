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

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ViewHolderItemLikes extends ViewHolderItemLikeBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private AdapterLikesImages adapter;

    ViewHolderItemLikes(ViewGroup parent, RecyclerView.RecycledViewPool viewPool) {
        super(parent, R.layout.view_item_images_likes, viewPool);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    protected void initList() {
        adapter = new AdapterLikesImages();
        rvItems.setAdapter(adapter);
        rvItems.addOnScrollListener(new ListenerScrollPhotos());
        rvItems.setNestedScrollingEnabled(false);
        rvItems.setHasFixedSize(true);
        OverScrollDecoratorHelper.setUpOverScroll(rvItems, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
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
        presenterAdapterLikes.onClickChat(getAdapterPosition());
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
            presenterAdapterLikes.onScrollPhotoChanged(newState, getAdapterPosition(), layoutManager.findFirstVisibleItemPosition());
        }
    }
}
