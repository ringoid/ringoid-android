/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterLikes;
import com.ringoid.view.presenter.callback.IPresenterLikesListener;
import com.ringoid.view.ui.adapter.AdapterLikes;
import com.ringoid.view.ui.adapter.ViewHolderItemLikeBase;
import com.ringoid.view.ui.adapter.ViewHolderItemLikes;

import javax.inject.Inject;

public class FragmentLikes extends FragmentFeedPage {

    @Inject
    IPresenterLikes presenterLikes;

    private IPresenterLikesListener listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterLikes.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container);
        initViews(view);
        presenterLikes.onCreateView();
        return view;
    }

    void initViews(View view) {
        super.initViews(view);
        rvItems.setAdapter(new AdapterLikes());
    }

    @Override
    protected void onScroll(int dy) {
        presenterLikes.onScroll(dy);
    }

    @Override
    protected void onScrollState(int newState, int firstVisibleItemPosition) {
        presenterLikes.onScrollState(newState, firstVisibleItemPosition);
    }

    @Override
    protected void onSwipeToRefresh() {
        presenterLikes.onSwipeToRefresh();
    }

    private class ListenerPresenter implements IPresenterLikesListener {

        @Override
        public boolean isPositionTop() {
            return rvItems.computeVerticalScrollOffset() == 0;
        }

        @Override
        public void scrollTop() {
            rvItems.smoothScrollToPosition(0);
        }

        @Override
        public void onLike(int adapterPosition) {
            RecyclerView.ViewHolder item = rvItems.findViewHolderForAdapterPosition(adapterPosition);
            if (item == null) return;
            ViewHolderItemLikeBase holder = (ViewHolderItemLikes) item;

            holder.setLiked(adapterPosition);

        }

        @Override
        public void onUnlike(int adapterPosition) {
            RecyclerView.ViewHolder item = rvItems.findViewHolderForAdapterPosition(adapterPosition);
            if (item == null) return;
            ViewHolderItemLikeBase holder = (ViewHolderItemLikes) item;

            holder.setUnliked(adapterPosition);
        }

        @Override
        public void scrollToPosition(int position) {
            rvItems.scrollToPosition(position);
        }

        @Override
        public void completeRefresh() {
            srlFeed.setRefreshing(false);
        }
    }
}
