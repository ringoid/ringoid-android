/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemMessage extends ViewHolderItemLikeBase {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private AdapterMessagesImages adapter;
    private ImageView ivMessage;

    ViewHolderItemMessage(ViewGroup parent) {
        super(parent, R.layout.view_item_messages_images);
        ApplicationRingoid.getComponent().inject(this);

        ivMessage = itemView.findViewById(R.id.ivMessage);
    }

    @Override
    protected void initList() {
        adapter = new AdapterMessagesImages();
        rvItems.setAdapter(adapter);
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
    }

    private void setMessageState(int position) {
        if (!presenterAdapterMessages.isMessagesExist(position)) {
            ivMessage.setImageDrawable(null);
            return;
        }

        ivMessage.setImageResource(presenterAdapterMessages.isMessagesNew(position)
                ? R.drawable.ic_mail_green_24dp
                : R.drawable.ic_mail_open_green_24dp);
    }
}
