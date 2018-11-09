/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.presenter.callback.IPresenterAdapterMessagesListener;

import javax.inject.Inject;

public class AdapterMessages extends AdapterBase {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private IPresenterAdapterMessagesListener listenerPresenter;
    private RecyclerView.RecycledViewPool viewPool;

    public AdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterMessages.setListener(listenerPresenter = new ListenerPresenter());
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemMessage(parent, viewPool);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterMessages.getItemsNum();
    }

    private class ListenerPresenter implements IPresenterAdapterMessagesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}

