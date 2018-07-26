package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterLikes;
import org.byters.ringoid.view.ui.util.DotsIndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemLikes extends ViewHolderBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private FrameLayout flDots;
    private DotsIndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterLikesImages adapter;

    public ViewHolderItemLikes(ViewGroup parent) {
        super(parent, R.layout.view_item_likes);
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
        dotsIndicatorHelper = new DotsIndicatorHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterLikes.getItemsNum(position));
    }
}
