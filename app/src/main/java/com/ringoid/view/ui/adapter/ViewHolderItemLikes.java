package com.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemLikes extends ViewHolderBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private FrameLayout flDots;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterLikesImages adapter;

    public ViewHolderItemLikes(ViewGroup parent) {
        super(parent, R.layout.view_item_images);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);

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
    }
}
