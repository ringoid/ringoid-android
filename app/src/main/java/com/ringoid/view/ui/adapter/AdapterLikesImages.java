/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterLikesImages;

import javax.inject.Inject;

public class AdapterLikesImages  extends RecyclerView.Adapter<ViewHolderItemLikesImages> {

    @Inject
    IPresenterAdapterLikesImages presenterAdapterLikesImages;

    private int adapterPosition;

    public AdapterLikesImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onBindViewHolder(ViewHolderItemLikesImages holder, int position) {
        holder.setData(adapterPosition, position);
    }

    @NonNull
    @Override
    public ViewHolderItemLikesImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemLikesImages(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterLikesImages.getItemsNum(adapterPosition);
    }

    public void setPosition(int position) {
        this.adapterPosition = position;
        notifyDataSetChanged();
    }
}