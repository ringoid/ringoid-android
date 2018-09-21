package com.ringoid.view.ui.adapter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ringoid.R;
import com.ringoid.view.ui.dialog.DialogReport;
import com.ringoid.view.ui.util.IndicatorHelper;

public abstract class ViewHolderItemLikeBase extends ViewHolderBase
        implements View.OnClickListener {

    LinearLayoutManager layoutManager;
    DialogReport dialogMenu;
    FrameLayout flDots;
    IndicatorHelper dotsIndicatorHelper;
    RecyclerView rvItems;
    View vChatEmpty, vChatFull;

    ViewHolderItemLikeBase(ViewGroup container, int layoutRes) {
        super(container, layoutRes);

        flDots = itemView.findViewById(R.id.flDots);

        itemView.findViewById(R.id.ivImageMenu).setOnClickListener(this);
        flDots.setOnClickListener(this);

        vChatEmpty = itemView.findViewById(R.id.fabChat);
        vChatFull = itemView.findViewById(R.id.ivChat);
        vChatFull.setOnClickListener(this);
        vChatEmpty.setOnClickListener(this);

        rvItems = itemView.findViewById(R.id.rvImages);
        layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvItems.setLayoutManager(layoutManager);
        new PagerSnapHelper().attachToRecyclerView(rvItems);

        initList();

    }

    protected abstract void initList();

    protected abstract boolean isChatEmpty(int position);

    protected abstract boolean isLikedAnyPhoto(int position);

    protected abstract void onClickChat();

    @Override
    void setData(int position) {
        setChatButtonVisible(position);
    }

    private void setChatButtonVisible(int position) {

        if (!isLikedAnyPhoto(position)) {
            vChatEmpty.setVisibility(View.GONE);
            vChatFull.setVisibility(View.GONE);
            return;
        }

        boolean isChatEmpty = isChatEmpty(position);

        vChatEmpty.setVisibility(isChatEmpty ? View.VISIBLE : View.GONE);
        vChatFull.setVisibility(isChatEmpty ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivImageMenu) {
            if (dialogMenu != null)
                dialogMenu.cancel();
            dialogMenu = new DialogReport(itemView.getContext());
            dialogMenu.show();
        }

        if (v.getId() == R.id.fabChat || v.getId() == R.id.ivChat) {
            onClickChat();
        }
    }

    public void setLiked(int position) {
        setChatButtonVisible(position);
    }

    public void setUnliked(int position) {
        setChatButtonVisible(position);
    }
}
