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
import com.ringoid.view.ui.dialog.DialogReport;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ViewHolderItemExplore extends ViewHolderBase implements View.OnClickListener {

    @Inject
    IPresenterAdapterExplore presenterAdapterExplore;

    private FrameLayout flDots;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterExploreImages adapter;

    private DialogReport dialogReport;
    private LinearLayoutManager layoutManager;

    public ViewHolderItemExplore(ViewGroup parent, RecyclerView.RecycledViewPool viewPool) {
        super(parent, R.layout.view_item_images_explore);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);
        flDots.setOnClickListener(this);
        itemView.findViewById(R.id.ivImageMenu).setOnClickListener(this);

        initList(viewPool);
    }

    private void initList(RecyclerView.RecycledViewPool viewPool) {
        rvItems = itemView.findViewById(R.id.rvImages);
        layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterExploreImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);
        rvItems.addOnScrollListener(new ListenerScrollPhotos());
        rvItems.setRecycledViewPool(viewPool);
        rvItems.setNestedScrollingEnabled(false);
        rvItems.setHasFixedSize(true);

        OverScrollDecoratorHelper.setUpOverScroll(rvItems, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        rvItems.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        dotsIndicatorHelper = IndicatorHelper.getLinesHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterExplore.getItemsNum(position));
        rvItems.scrollToPosition(presenterAdapterExplore.getSelectedPhotoPosition(position));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.flDots)
            presenterAdapterExplore.onClickScrolls();
        if (v.getId() == R.id.ivImageMenu) {
            if (dialogReport != null)
                dialogReport.cancel();
            dialogReport = new DialogReport(itemView.getContext());
            dialogReport.show();
        }
    }

    private class ListenerScrollPhotos extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            presenterAdapterExplore.onScrollPhotoChanged(newState, getAdapterPosition() - 1, layoutManager.findFirstVisibleItemPosition());
        }
    }
}
