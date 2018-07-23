package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterRank;
import org.byters.ringoid.view.ui.adapter.callback.IPresenterAdapterRankImagesListener;
import org.byters.ringoid.view.ui.util.DotsIndicatorHelper;
import org.byters.ringoid.view.ui.util.ListenerScrollComplete;
import org.byters.ringoid.view.ui.util.listener.IScrollCompleteCallback;

import javax.inject.Inject;

public class AdapterRank extends AdapterBase {

    @Inject
    IPresenterAdapterRank presenterAdapterRank;

    public AdapterRank() {
        ApplicationRingoid.getComponent().inject(this);
    }


    @Override
    public int getItemCount() {
        return presenterAdapterRank.getItemsNum();
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderItem(parent);
    }

    private class HolderItem extends ViewHolderBase {

        private TextView tvRank;
        private AdapterRankImages adapter;
        private RecyclerView rvItems;
        private IPresenterAdapterRankImagesListener listenerAdapterImages;
        private LinearLayoutManager layoutManager;
        private FrameLayout flDots;
        private ScrollCallback scrollCallback;
        private DotsIndicatorHelper dotsIndicatorHelper;

        HolderItem(ViewGroup parent) {
            super(parent, R.layout.view_item_rank);
            tvRank = itemView.findViewById(R.id.tvRank);
            flDots = itemView.findViewById(R.id.flDots);

            initList();
        }

        private void initList() {
            rvItems = itemView.findViewById(R.id.rvImages);
            rvItems.setLayoutManager(layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvItems.setAdapter(adapter = new AdapterRankImages());
            new PagerSnapHelper().attachToRecyclerView(rvItems);
            rvItems.addOnScrollListener(new ListenerScrollComplete(scrollCallback = new ScrollCallback()));
            dotsIndicatorHelper = new DotsIndicatorHelper(flDots, rvItems, layoutManager);
        }

        @Override
        void setData(int position) {
            String rank = presenterAdapterRank.getRank(position);
            tvRank.setText(TextUtils.isEmpty(rank) ? "" : rank);
            adapter.setPosition(position);

            scrollCallback.updateLayout(position, 0);
            dotsIndicatorHelper.updateData(presenterAdapterRank.getItemsNum(position));
        }

        private class ScrollCallback implements IScrollCompleteCallback {
            @Override
            public void onScrollComplete() {
                updateLayout(getAdapterPosition(), layoutManager.findFirstVisibleItemPosition());
            }

            void updateLayout(int position, int itemPosition) {

                int height = presenterAdapterRank.getItemHeight(itemView.getContext(), position, itemPosition);

                ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

                rvItems.setLayoutParams(params);

                adapter.loadImage(rvItems.findViewHolderForLayoutPosition(itemPosition));
            }

        }
    }

}
