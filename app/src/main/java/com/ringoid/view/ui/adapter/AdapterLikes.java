/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.presenter.callback.IPresenterAdapterLikesListener;

import javax.inject.Inject;

public class AdapterLikes extends AdapterFeed {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    private ListenerPresenter listenerPresenter;
    private RecyclerView.RecycledViewPool viewPool;

    public AdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterLikes.setListener(listenerPresenter = new ListenerPresenter());
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    protected int getFeedTitle() {
        return R.string.title_likes;
    }

    @Override
    protected int getItemsNum() {
        return presenterAdapterLikes.getItemsNum();
    }

    @Override
    protected ViewHolderBase getViewHolder(ViewGroup parent) {
        return new ViewHolderItemLikes(parent, viewPool);
    }

    private class ListenerPresenter implements IPresenterAdapterLikesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
