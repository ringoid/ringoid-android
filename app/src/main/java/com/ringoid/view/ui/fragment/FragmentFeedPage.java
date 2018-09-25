package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.R;

abstract class FragmentFeedPage extends FragmentBase {

    RecyclerView rvItems;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout srlFeed;
    private ListenerRefresh listenerRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listenerRefresh = new ListenerRefresh();
    }

    void initViews(View view) {
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(layoutManager = new LinearLayoutManager(view.getContext()));
        rvItems.addOnScrollListener(new OnScrollListener());
        rvItems.addItemDecoration(new ItemDecoration(getContext()));


        srlFeed = view.findViewById(R.id.srlFeed);
        srlFeed.setOnRefreshListener(listenerRefresh);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_page, container, false);
    }

    protected abstract void onScrollState(int newState, int firstVisibleItemPosition);

    protected abstract void onScroll(int dy);

    protected abstract void onSwipeToRefresh();

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            onScroll(dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            onScrollState(newState, layoutManager.findFirstVisibleItemPosition());
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

    private class ListenerRefresh implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            onSwipeToRefresh();
        }
    }
}
