/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.presenter.callback.IPresenterAdapterLikesListener;

import javax.inject.Inject;

public class AdapterLikes extends AdapterBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private ListenerPresenter listenerPresenter;

    public AdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterLikes.setListener(listenerPresenter = new ListenerPresenter());
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemLikes(parent);
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
