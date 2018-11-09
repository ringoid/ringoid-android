/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.presenter.callback.IPresenterAdapterLikesListener;

import javax.inject.Inject;

public class AdapterLikes extends AdapterBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private ListenerPresenter listenerPresenter;
    private RecyclerView.RecycledViewPool viewPool;

    public AdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterLikes.setListener(listenerPresenter = new ListenerPresenter());
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemLikes(parent, viewPool);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterLikes.getItemsNum();
    }

    private class ListenerPresenter implements IPresenterAdapterLikesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
