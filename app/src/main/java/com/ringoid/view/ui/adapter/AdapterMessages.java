/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.presenter.callback.IPresenterAdapterMessagesListener;

import javax.inject.Inject;

public class AdapterMessages extends AdapterFeed {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private IPresenterAdapterMessagesListener listenerPresenter;
    private RecyclerView.RecycledViewPool viewPool;

    public AdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterMessages.setListener(listenerPresenter = new ListenerPresenter());
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    protected int getFeedTitle() {
        return R.string.title_messages;
    }

    @Override
    protected int getItemsNum() {
        return presenterAdapterMessages.getItemsNum();
    }

    @Override
    protected ViewHolderBase getViewHolder(ViewGroup parent) {
        return new ViewHolderItemMessage(parent, viewPool);
    }

    private class ListenerPresenter implements IPresenterAdapterMessagesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}

