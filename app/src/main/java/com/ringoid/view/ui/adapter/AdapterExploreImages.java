/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterExploreImages;

import javax.inject.Inject;

public class AdapterExploreImages extends RecyclerView.Adapter<ViewHolderItemExploreImages> {

    @Inject
    IPresenterAdapterExploreImages presenterAdapterExploreImages;

    private int adapterPosition;

    public AdapterExploreImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onBindViewHolder(ViewHolderItemExploreImages holder, int position) {
        holder.setData(adapterPosition, position);
    }

    @NonNull
    @Override
    public ViewHolderItemExploreImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemExploreImages(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterExploreImages.getItemsNum(adapterPosition);
    }

    public void setPosition(int position) {
        this.adapterPosition = position;
        notifyDataSetChanged();
    }
}
