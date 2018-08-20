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
import com.ringoid.view.presenter.IPresenterAdapterExplore;
import com.ringoid.view.ui.util.IndicatorHelper;
import com.ringoid.view.ui.util.LinesIndicator;

import javax.inject.Inject;

public class ViewHolderItemExplore extends ViewHolderBase implements View.OnClickListener {

    @Inject
    IPresenterAdapterExplore presenterAdapterExplore;

    private FrameLayout flDots;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterExploreImages adapter;

    public ViewHolderItemExplore(ViewGroup parent) {
        super(parent, R.layout.view_item_images);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);
        flDots.setOnClickListener(this);

        initList();
    }

    private void initList() {
        rvItems = itemView.findViewById(R.id.rvImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterExploreImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        dotsIndicatorHelper = IndicatorHelper.getLinesHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterExplore.getItemsNum(position));
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.flDots)
            presenterAdapterExplore.onClickScrolls();
    }
}
