/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemMessage extends ViewHolderItemLikeBase {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private AdapterMessagesImages adapter;
    private ImageView ivMessage;

    ViewHolderItemMessage(ViewGroup parent, RecyclerView.RecycledViewPool viewPool) {
        super(parent, R.layout.view_item_messages_images, viewPool);
        ApplicationRingoid.getComponent().inject(this);

        ivMessage = itemView.findViewById(R.id.ivMessage);
    }

    @Override
    protected void initList() {
        adapter = new AdapterMessagesImages();
        rvItems.setAdapter(adapter);
        rvItems.addOnScrollListener(new ListenerScrollPhotos());
        rvItems.setNestedScrollingEnabled(false);
        rvItems.setHasFixedSize(true);
        dotsIndicatorHelper = IndicatorHelper.getLinesAccentGreenHelper(flDots, rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
    }

    @Override
    protected boolean isChatEmpty(int position) {
        return presenterAdapterMessages.isChatEmpty(position);
    }

    @Override
    protected boolean isLikedAnyPhoto(int position) {
        return presenterAdapterMessages.isLikedAnyPhoto(position);
    }

    @Override
    protected void onClickChat() {
        presenterAdapterMessages.onClickChat(getAdapterPosition());
    }

    @Override
    void setData(int position) {
        super.setData(position);
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterMessages.getItemsNum(position));
        setMessageState(position);
        rvItems.scrollToPosition(presenterAdapterMessages.getSelectedPhotoPosition(position));

    }

    @Override
    protected boolean isControlsVisible() {
        return presenterAdapterMessages.isControlsVisible();
    }

    private void setMessageState(int position) {
        if (!presenterAdapterMessages.isMessagesExist(position) || cacheInterfaceState.isDialogComposeShown()) {
            ivMessage.setImageDrawable(null);
            return;
        }

        ivMessage.setImageResource(presenterAdapterMessages.isMessagesNew(position)
                ? R.drawable.ic_mail_green_24dp
                : R.drawable.ic_mail_open_green_24dp);
    }

    private class ListenerScrollPhotos extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            presenterAdapterMessages.onScrollPhotoChanged(newState, getAdapterPosition(), layoutManager.findFirstVisibleItemPosition());
        }
    }
}
