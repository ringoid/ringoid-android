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
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterFeedPage;
import com.ringoid.view.presenter.callback.IPresenterFeedPageListener;

import javax.inject.Inject;

public abstract class FragmentFeedPage extends FragmentBase implements View.OnClickListener {

    RecyclerView rvItems;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout srlFeed;
    View vPhotoEmpty;
    TextView tvNoPhoto;

    @Inject
    INavigator navigator;

    @Inject
    IPresenterFeedPage presenterFeedPage;

    private ListenerRefresh listenerRefresh;
    private IPresenterFeedPageListener listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        listenerRefresh = new ListenerRefresh();
        presenterFeedPage.setListener(listenerPresenter = new ListenerPresenter());
    }

    void initViews(View view) {
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(layoutManager = new LinearLayoutManager(view.getContext()));
        rvItems.addOnScrollListener(new OnScrollListener());
        rvItems.addItemDecoration(new ItemDecoration(getContext()));

        vPhotoEmpty = view.findViewById(R.id.llNoPhoto);
        tvNoPhoto = view.findViewById(R.id.tvMessageNoPhoto);

        srlFeed = view.findViewById(R.id.srlFeed);
        srlFeed.setOnRefreshListener(listenerRefresh);
        srlFeed.setProgressViewOffset(false,
                (int) getResources().getDimension(R.dimen.toolbar_height_with_statusbar),
                (int) (getResources().getDimension(R.dimen.toolbar_height_with_statusbar) * 1.5f));
        srlFeed.setColorSchemeResources(R.color.colorAccent);
        view.findViewById(R.id.tvPhotoAdd).setOnClickListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_page, container, false);
    }

    protected abstract void onScrollState(int newState, int firstVisibleItemPosition);

    protected abstract void onSwipeToRefresh();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvPhotoAdd)
            navigator.navigatePhotoAdd();
    }

    void showErrorNoPhoto(int messageRes) {
        srlFeed.setVisibility(View.GONE);
        vPhotoEmpty.setVisibility(View.VISIBLE);
        tvNoPhoto.setText(messageRes);
    }

    private void onScroll(int dy, int scrollSum) {
        presenterFeedPage.onScroll(dy, scrollSum);
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            onScroll(dy, rvItems.computeVerticalScrollOffset());
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            onScrollState(newState, layoutManager.findFirstVisibleItemPosition());
        }
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {

        private int margin, marginFirst;

        ItemDecoration(Context context) {
            margin = (int) context.getResources().getDimension(R.dimen.divider);
            marginFirst = (int) getResources().getDimension(R.dimen.toolbar_height_with_statusbar);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildLayoutPosition(view);

            if (position != 0)
                outRect.top = margin;
            else
                outRect.top = marginFirst;
        }

    }

    private class ListenerRefresh implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            onSwipeToRefresh();
        }
    }

    private class ListenerPresenter implements IPresenterFeedPageListener {
        @Override
        public void scrollSmoothBy(int y) {
            rvItems.smoothScrollBy(0, y);
        }

        @Override
        public boolean isPositionTop() {
            return rvItems.computeVerticalScrollOffset() == 0;
        }

        @Override
        public void scrollTop() {
            rvItems.scrollToPosition(0);
        }

        @Override
        public void scrollToPosition(int position) {
            rvItems.scrollToPosition(position);
        }

        @Override
        public void completeRefresh() {
            srlFeed.setRefreshing(false);
        }

        @Override
        public void showViewNoPhoto(int messageRes) {
            showErrorNoPhoto(messageRes);
        }
    }
}
