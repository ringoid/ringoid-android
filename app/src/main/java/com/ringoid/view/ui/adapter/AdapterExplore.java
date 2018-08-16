/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterExplore;

import javax.inject.Inject;

public class AdapterExplore extends AdapterBase {

    @Inject
    IPresenterAdapterExplore presenterAdapterExplore;

    public AdapterExplore(){
        ApplicationRingoid.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemExplore(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterExplore.getItemsNum();
    }

}
