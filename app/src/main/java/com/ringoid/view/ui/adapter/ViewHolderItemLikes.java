/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
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
        dotsIndicatorHelper = IndicatorHelper.getLinesAccentHelper(flDots, rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
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
        dotsIndicatorHelper.updateData(presenterAdapterLikes.getItemsNum(position));
    }
}
