package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.R;

public class AdapterRankSexSame extends AdapterBase {

    @Override
    public int getItemCount() {
        return 4;
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderItem(parent);
    }

    private class HolderItem extends ViewHolderBase {

        private TextView tvLikes;

        HolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image_rank_sex_same);
            initList();
            tvLikes = itemView.findViewById(R.id.tvLikes);
        }

        private void initList() {
            RecyclerView rvItems = itemView.findViewById(R.id.rvImages);
            rvItems.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvItems.setAdapter(new AdapterRankImagesSexSame());
        }

        @Override
        void setData(int position) {
            tvLikes.setText("4");
            tvLikes.setVisibility(View.VISIBLE);
        }
    }
}
