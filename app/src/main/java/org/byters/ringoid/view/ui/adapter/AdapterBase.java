package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.RecyclerView;

public abstract class AdapterBase extends RecyclerView.Adapter<ViewHolderBase> {

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        holder.setData(position);
    }

}