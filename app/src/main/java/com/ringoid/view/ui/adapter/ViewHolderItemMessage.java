/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.ui.dialog.DialogReport;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemMessage extends ViewHolderBase
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private DialogReport dialogMenu;
    private FrameLayout flDots;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterMessagesImages adapter;
    private ImageView ivMessage;

    ViewHolderItemMessage(ViewGroup parent) {
        super(parent, R.layout.view_item_messages_images);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);

        itemView.findViewById(R.id.ivImageMenu).setOnClickListener(this);
        ivMessage = itemView.findViewById(R.id.ivMessage);
        flDots.setOnClickListener(this);

        initList();
    }

    private void initList() {
        rvItems = itemView.findViewById(R.id.rvImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterMessagesImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        dotsIndicatorHelper = IndicatorHelper.getLinesAccentGreenHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterMessages.getItemsNum(position));
        ivMessage.setImageResource(presenterAdapterMessages.isMessagesNew(position)
                ? R.drawable.ic_message_full_green_24dp
                : presenterAdapterMessages.isMessagesExist(position)
                ? R.drawable.ic_message_dots_green_24dp
                : R.drawable.ic_message_border_green_24dp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivImageMenu) {
            if (dialogMenu != null)
                dialogMenu.cancel();
            dialogMenu = new DialogReport(itemView.getContext());
            dialogMenu.show();
        }

        if (v.getId() == R.id.flDots)
            presenterAdapterMessages.onClickScrolls();

    }
}
