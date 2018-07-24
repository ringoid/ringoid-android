package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterExplore;
import org.byters.ringoid.view.ui.util.DotsIndicatorHelper;
import org.byters.ringoid.view.ui.util.ListenerScrollComplete;

import javax.inject.Inject;

public class ViewHolderItemExplore extends ViewHolderBase {

    @Inject
    IPresenterAdapterExplore presenterAdapterExplore;

    private FrameLayout flDots;
    private DotsIndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterExploreImages adapter;

    public ViewHolderItemExplore(ViewGroup parent) {
        super(parent, R.layout.view_item_explore);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);

        initList();
    }

    private void initList() {
        rvItems = itemView.findViewById(R.id.rvImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterExploreImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        dotsIndicatorHelper = new DotsIndicatorHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {

        int height = presenterAdapterExplore.getItemHeight(itemView.getContext(), position);

        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        rvItems.setLayoutParams(params);

        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterExplore.getItemsNum(position));
    }
}
