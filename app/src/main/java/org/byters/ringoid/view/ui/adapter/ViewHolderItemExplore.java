package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterExplore;
import org.byters.ringoid.view.ui.util.IndicatorHelper;
import org.byters.ringoid.view.ui.util.LinesIndicator;

import javax.inject.Inject;

public class ViewHolderItemExplore extends ViewHolderBase {

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
}
