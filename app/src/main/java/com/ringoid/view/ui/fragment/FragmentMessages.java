/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.view.presenter.IPresenterMessages;
import com.ringoid.view.presenter.callback.IPresenterMessagesListener;
import com.ringoid.view.ui.adapter.AdapterMessages;

import javax.inject.Inject;

public class FragmentMessages extends FragmentFeedPage {

    @Inject
    IPresenterMessages presenterMessages;

    private ListenerPresenterMessages listenerPresenterMessages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterMessages.setListener(listenerPresenterMessages = new ListenerPresenterMessages());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container);
        initViews(view);
        presenterMessages.onCreateView();
        return view;
    }

    void initViews(View view) {
        super.initViews(view);
        rvItems.setAdapter(new AdapterMessages());
    }

    @Override
    protected void onScroll(int dy) {
        presenterMessages.onScroll(dy);
    }

    @Override
    protected void onScrollState(int newState, int firstVisibleItemPosition) {
        presenterMessages.onScrollState(newState, firstVisibleItemPosition);
    }

    @Override
    protected void onSwipeToRefresh() {
        presenterMessages.onSwipeRefresh();
    }

    private class ListenerPresenterMessages implements IPresenterMessagesListener {

        @Override
        public boolean isPositionTop() {
            return rvItems.computeVerticalScrollOffset() == 0;
        }

        @Override
        public void completeRefresh() {
            srlFeed.setRefreshing(false);
        }

        @Override
        public void showViewNoPhoto(int messageRes) {
            showErrorNoPhoto(messageRes);
        }

        @Override
        public void scrollToTop() {
            rvItems.smoothScrollToPosition(0);
        }

        @Override
        public void scrollToPosition(int positionScrollPageMessages) {
            rvItems.scrollToPosition(positionScrollPageMessages);
        }

    }
}
