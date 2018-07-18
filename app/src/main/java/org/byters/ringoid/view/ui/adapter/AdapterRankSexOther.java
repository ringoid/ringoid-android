package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.byters.ringoid.R;

public class AdapterRankSexOther extends AdapterBase {

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderItem(parent);
    }

    private class HolderItem extends ViewHolderBase {

        HolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image_rank_sex_other);
            initList();
        }

        private void initList() {
            RecyclerView rvItems = itemView.findViewById(R.id.rvImages);
            rvItems.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvItems.setAdapter(new AdapterRankImagesSexOther());
        }

        @Override
        void setData(int position) {

        }
    }
}
