/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterExplore;
import com.ringoid.view.presenter.callback.IPresenterAdapterExploreListener;

import javax.inject.Inject;

public class AdapterExplore extends AdapterFeed {

    @Inject
    IPresenterAdapterExplore presenterAdapterExplore;

    private IPresenterAdapterExploreListener listenerPresenter;

    public AdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterExplore.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Override
    protected ViewHolderBase getViewHolder(ViewGroup parent) {
        return new ViewHolderItemExplore(parent);
    }

    @Override
    protected int getFeedSubtitle() {
        return R.string.subtitle_explore;
    }

    @Override
    protected int getFeedTitle() {
        return R.string.title_explore;
    }

    @Override
    protected int getItemsNum() {
        return presenterAdapterExplore.getItemsNum();
    }

    private class ListenerPresenter implements IPresenterAdapterExploreListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
