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
import com.ringoid.R;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.IPresenterLikes;
import com.ringoid.view.presenter.callback.IPresenterLikesListener;
import com.ringoid.view.ui.adapter.AdapterLikes;
import com.ringoid.view.ui.adapter.ViewHolderItemLikeBase;
import com.ringoid.view.ui.adapter.ViewHolderItemLikes;

import javax.inject.Inject;

public class FragmentLikes extends FragmentInnerTab {

    @Inject
    IPresenterLikes presenterLikes;

    private IPresenterLikesListener listenerPresenter;

    @Override
    public PAGE_ENUM getPage() {
        return PAGE_ENUM.LIKES_LIKES;
    }

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
        rvItems.setScrollBarColor(getContext().getResources().getColor(R.color.app_pink));
    }

    @Override
    protected void onShow(int state) {

    }

    @Override
    protected void onScrollState(int newState, int firstVisibleItemPosition, int offset) {
        presenterLikes.onScrollState(newState, firstVisibleItemPosition, getOffset(offset, firstVisibleItemPosition));
    }

    private int getOffset(int offset, int firstVisibleItemPosition) {
        if (firstVisibleItemPosition != 0)
            return offset;
        return (int) (offset - getContext().getResources().getDimension(R.dimen.likesTitleHeight));
    }

    @Override
    protected void onSwipeToRefresh() {
        presenterLikes.onSwipeToRefresh();
    }

    private class ListenerPresenter implements IPresenterLikesListener {

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


    }
}
