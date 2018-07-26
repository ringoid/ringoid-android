package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.view.presenter.IPresenterAdapterMessagesImages;

import javax.inject.Inject;

public class AdapterMessagesImages extends RecyclerView.Adapter<ViewHolderItemMessagesImages> {

    @Inject
    IPresenterAdapterMessagesImages presenterAdapterMessagesImages;

    private int adapterPosition;

    public AdapterMessagesImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onBindViewHolder(ViewHolderItemMessagesImages holder, int position) {
        holder.setData(adapterPosition, position);
    }

    @NonNull
    @Override
    public ViewHolderItemMessagesImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemMessagesImages(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterMessagesImages.getItemsNum(adapterPosition);
    }

    public void setPosition(int position) {
        this.adapterPosition = position;
        notifyDataSetChanged();
    }
}
