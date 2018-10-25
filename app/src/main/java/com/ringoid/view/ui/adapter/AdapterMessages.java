/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

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

    public AdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterMessages.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Override
    protected int getFeedSubtitle() {
        return R.string.subtitle_messages;
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
        return new ViewHolderItemMessage(parent);
    }

    private class ListenerPresenter implements IPresenterAdapterMessagesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}

