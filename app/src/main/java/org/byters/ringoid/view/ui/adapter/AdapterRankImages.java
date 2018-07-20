package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.byters.ringoid.R;

class AdapterRankImages extends AdapterBase {
    private int position;

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(parent);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    private class ViewHolderItem extends ViewHolderBase {
        private ImageView ivItem;

        //todo dots menu like, superlike, report, block

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image);
            ivItem = itemView.findViewById(R.id.ivContent);
        }

        @Override
        void setData(int position) {
            Glide.with(itemView.getContext())
                    .load("https://placeimg.com/400/300")
                    .into(ivItem);
        }
    }
}
