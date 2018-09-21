/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterExplore;
import com.ringoid.view.presenter.callback.IPresenterAdapterExploreListener;

import javax.inject.Inject;

public class AdapterExplore extends AdapterBase {

    @Inject
    IPresenterAdapterExplore presenterAdapterExplore;

    private IPresenterAdapterExploreListener listenerPresenter;

    public AdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterExplore.setListener(listenerPresenter = new ListenerPresenter());
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

    private class ListenerPresenter implements IPresenterAdapterExploreListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
