package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterMessages;

import javax.inject.Inject;

public class AdapterMessages extends AdapterBase {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    public AdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemMessage(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterMessages.getItemsNum();
    }

}
