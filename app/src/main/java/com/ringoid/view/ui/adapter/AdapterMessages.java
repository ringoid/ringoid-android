/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.presenter.callback.IPresenterAdapterMessagesListener;

import javax.inject.Inject;

public class AdapterMessages extends AdapterBase {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private IPresenterAdapterMessagesListener listenerPresenter;

    public AdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterMessages.setListener(listenerPresenter = new ListenerPresenter());
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

    private class ListenerPresenter implements IPresenterAdapterMessagesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
