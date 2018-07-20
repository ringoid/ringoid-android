package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterRank;

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

        HolderItem(ViewGroup parent) {
            super(parent, R.layout.view_item_rank);
            initList();
            tvRank = itemView.findViewById(R.id.tvRank);
        }

        private void initList() {
            RecyclerView rvItems = itemView.findViewById(R.id.rvImages);
            rvItems.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvItems.setAdapter(adapter = new AdapterRankImages());
        }

        @Override
        void setData(int position) {
            String rank = presenterAdapterRank.getRank(position);
            tvRank.setText(TextUtils.isEmpty(rank) ? "" : rank);
            adapter.setPosition(position);
        }
    }
}
