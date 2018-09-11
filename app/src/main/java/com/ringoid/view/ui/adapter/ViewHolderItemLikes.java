/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.ui.dialog.DialogReport;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemLikes extends ViewHolderBase implements View.OnClickListener {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;


    private View vChatEmpty, vChatFull;

    private FrameLayout flDots;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterLikesImages adapter;

    private DialogReport dialogReport;

    public ViewHolderItemLikes(ViewGroup parent) {
        super(parent, R.layout.view_item_images_likes);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);

        flDots.setOnClickListener(this);
        itemView.findViewById(R.id.ivImageMenu).setOnClickListener(this);

        vChatEmpty = itemView.findViewById(R.id.fabChat);
        vChatFull = itemView.findViewById(R.id.ivChat);
        vChatFull.setOnClickListener(this);
        vChatEmpty.setOnClickListener(this);

        initList();
    }

    private void initList() {
        rvItems = itemView.findViewById(R.id.rvImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterLikesImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        dotsIndicatorHelper = IndicatorHelper.getLinesAccentHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterLikes.getItemsNum(position));
        setChatButtonVisible(position);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.flDots)
            presenterAdapterLikes.onClickScrolls();

        if (v.getId() == R.id.ivImageMenu) {
            if (dialogReport != null)
                dialogReport.cancel();
            dialogReport = new DialogReport(itemView.getContext());
            dialogReport.show();
        }

        if (v.getId() == R.id.fabChat || v.getId() == R.id.ivChat) {
            presenterAdapterLikes.onClickChat(getAdapterPosition());
        }
    }

    private void setChatButtonVisible(int position) {

        if (!presenterAdapterLikes.isLikedAnyPhoto(position)) {
            vChatEmpty.setVisibility(View.GONE);
            vChatFull.setVisibility(View.GONE);
            return;
        }

        boolean isChatEmpty = presenterAdapterLikes.isChatEmpty(position);

        vChatEmpty.setVisibility(isChatEmpty ? View.VISIBLE : View.GONE);
        vChatFull.setVisibility(isChatEmpty ? View.GONE : View.VISIBLE);
    }
}
