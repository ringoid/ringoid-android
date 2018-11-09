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
import com.ringoid.view.presenter.IPresenterFeedPage;
import com.ringoid.view.presenter.callback.IPresenterFeedPageListener;
import com.ringoid.view.ui.view.RecyclerViewScrollbarColored;

import javax.inject.Inject;

public abstract class FragmentInnerTab extends FragmentBase implements View.OnClickListener {

    static final int STATE_NO_PHOTO = 0;
    static final int STATE_EMPTY = 1;
    static final int STATE_CONTENT = 2;

    @Inject
    IPresenterFeedPage presenterFeedPage;

    RecyclerViewScrollbarColored rvItems;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout srlFeed;
    TextView tvNoPhotoMessage, tvNoContentMessage;
    View vNoPhotoContainer;

    private IPresenterFeedPageListener listenerPresenter;
    private ListenerRefresh listenerRefresh;

    protected abstract void onShow(int state);

    protected abstract void onScrollState(int newState, int firstVisibleItemPosition, int offset);

    protected abstract void onSwipeToRefresh();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);

        presenterFeedPage.setListener(listenerPresenter = new ListenerPresenter());
        listenerRefresh = new ListenerRefresh();
    }

    private void onScroll(int dy, int scrollSum) {
        presenterFeedPage.onScroll(dy, scrollSum);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_page, container, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvPhotoAdd)
            navigator.navigatePhotoAdd();
    }

    void showErrorNoPhoto(int messageRes) {
        srlFeed.setVisibility(View.GONE);
        tvNoContentMessage.setVisibility(View.GONE);
        vNoPhotoContainer.setVisibility(View.VISIBLE);
        tvNoPhotoMessage.setText(messageRes);
        onShow(STATE_NO_PHOTO);
    }

    void showEmpty(int messageRes) {
        srlFeed.setVisibility(View.GONE);
        vNoPhotoContainer.setVisibility(View.GONE);
        tvNoContentMessage.setVisibility(View.VISIBLE);
        tvNoContentMessage.setText(messageRes);
        onShow(STATE_EMPTY);
    }

    void showContent() {
        srlFeed.setVisibility(View.VISIBLE);
        vNoPhotoContainer.setVisibility(View.GONE);
        tvNoContentMessage.setVisibility(View.GONE);
        onShow(STATE_CONTENT);
    }

    void initViews(View view) {
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(layoutManager = new LinearLayoutManager(view.getContext()));
        rvItems.addOnScrollListener(new OnScrollListener());
        rvItems.addItemDecoration(getItemDecoration());

        vNoPhotoContainer = view.findViewById(R.id.llNoPhoto);
        tvNoPhotoMessage = view.findViewById(R.id.tvMessageNoPhoto);

        tvNoContentMessage = view.findViewById(R.id.tvEmpty);

        srlFeed = view.findViewById(R.id.srlFeed);
        srlFeed.setOnRefreshListener(listenerRefresh);
        srlFeed.setColorSchemeResources(R.color.colorAccent);

        view.findViewById(R.id.tvPhotoAdd).setOnClickListener(this);
    }

    RecyclerView.ItemDecoration getItemDecoration() {
        return new ItemDecoration(getContext());
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

            int pos = layoutManager.findFirstVisibleItemPosition();
            View firstItemView = layoutManager.findViewByPosition(pos);
            int offset = firstItemView.getTop();
            onScrollState(newState, pos, offset);
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
            if (getContext() == null || rvItems == null) return;
            rvItems.smoothScrollBy(0, y);
        }

        @Override
        public boolean isPositionTop() {
            if (getContext() == null || rvItems == null) return false;
            return rvItems.computeVerticalScrollOffset() == 0;
        }

        @Override
        public void scrollTop() {
            if (getContext() == null || rvItems == null) return;
            rvItems.scrollToPosition(0);
        }

        @Override
        public void scrollToPosition(int position, int offset) {
            if (getContext() == null || layoutManager == null) return;
            layoutManager.scrollToPositionWithOffset(position, offset);
        }

        @Override
        public void completeRefresh() {
            if (getContext() == null || srlFeed == null) return;
            srlFeed.setRefreshing(false);
        }

        @Override
        public void showViewNoPhoto(int messageRes) {
            if (getContext() == null) return;
            showErrorNoPhoto(messageRes);
        }
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        private int marginTopFirst;
        private int heightBottom;
        private int margin;

        ItemDecoration(Context context) {
            margin = (int) context.getResources().getDimension(R.dimen.divider);
            marginTopFirst = (int) context.getResources().getDimension(R.dimen.likesTitleHeight);

            heightBottom = Math.max(0,
                    getResources().getDisplayMetrics().heightPixels - (int) (getResources().getDisplayMetrics().widthPixels / 3f * 4)
                            - (int) getResources().getDimension(R.dimen.statusbar));

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildLayoutPosition(view);

            if (position != 0)
                outRect.top = margin;
            else
                outRect.top = marginTopFirst;

            if (position == state.getItemCount() - 1)
                outRect.bottom = heightBottom;
        }
    }
}
