package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

abstract class ViewHolderBase extends RecyclerView.ViewHolder {

    ViewHolderBase(ViewGroup container, int layoutRes) {
        super(LayoutInflater.from(container.getContext()).inflate(layoutRes, container, false));
    }

    abstract void setData(int position);
}
