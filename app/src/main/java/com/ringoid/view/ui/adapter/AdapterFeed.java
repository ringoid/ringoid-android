package com.ringoid.view.ui.adapter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.R;

abstract class AdapterFeed extends AdapterBase {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;

    @Override
    final public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    final public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType == TYPE_HEADER ? new ViewHolderHeader(parent) : getViewHolder(parent);
    }

    @Override
    final public int getItemCount() {
        return getItemsNum() + 1;
    }

    @Override
    final public void onBindViewHolder(ViewHolderBase holder, int position) {
        holder.setData(position == 0 ? 0 : position - 1);
    }

    protected abstract int getFeedTitle();

    protected abstract int getItemsNum();

    protected abstract ViewHolderBase getViewHolder(ViewGroup parent);

    private class ViewHolderHeader extends ViewHolderBase {
        private TextView tvTitle;

        public ViewHolderHeader(ViewGroup parent) {
            super(parent, R.layout.view_feed_header);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        @Override
        void setData(int position) {
            tvTitle.setText(getFeedTitle());
        }
    }
}
