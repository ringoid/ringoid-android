/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterLikes;
import com.ringoid.view.presenter.callback.IPresenterLikesListener;
import com.ringoid.view.ui.adapter.AdapterLikes;
import com.ringoid.view.ui.adapter.ViewHolderItemLikeBase;
import com.ringoid.view.ui.adapter.ViewHolderItemLikes;

import javax.inject.Inject;

public class FragmentLikes extends FragmentBase {

    @Inject
    IPresenterLikes presenterLikes;

    private IPresenterLikesListener listenerPresenter;

    private RecyclerView rvItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationRingoid.getComponent().inject(this);
        presenterLikes.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likes, container, false);

        initViews(view);

        presenterLikes.onCreateView();
        return view;
    }

    private void initViews(View view) {
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvItems.setAdapter(new AdapterLikes());
        rvItems.addOnScrollListener(new OnScrollListener());
        rvItems.addItemDecoration(new ItemDecoration(getContext()));
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            presenterLikes.onScroll(dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            presenterLikes.onScrollState(newState);
        }
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {

        private int margin;

        ItemDecoration(Context context) {
            margin = (int) context.getResources().getDimension(R.dimen.divider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildLayoutPosition(view);

            if (position != 0)
                outRect.top = margin;
        }

    }

    private class ListenerPresenter implements IPresenterLikesListener {
        @Override
        public boolean isPositionTop() {
            return rvItems.getScrollY() == 0;
        }

        @Override
        public void scrollTop() {
            rvItems.scrollToPosition(0);
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
    }
}
