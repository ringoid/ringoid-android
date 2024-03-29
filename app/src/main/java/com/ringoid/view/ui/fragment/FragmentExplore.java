/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.IPresenterExplore;
import com.ringoid.view.presenter.callback.IPresenterExploreListener;
import com.ringoid.view.ui.adapter.AdapterExplore;

import javax.inject.Inject;

public class FragmentExplore extends FragmentFeedPage {

    @Inject
    IPresenterExplore presenterExplore;

    private IPresenterExploreListener listenerPresenter;

    @Override
    public PAGE_ENUM getPage() {
        return PAGE_ENUM.FEED_EXPLORE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterExplore.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container);
        initViews(view);
        presenterExplore.onCreateView();
        return view;
    }

    void initViews(View view) {
        super.initViews(view);
        rvItems.setAdapter(new AdapterExplore());
    }

    @Override
    protected String getTitle() {
        return getContext().getResources().getString(R.string.title_explore);
    }

    @Override
    protected void onScrollState(int newState, int firstVisibleItemPosition, int offset) {
        presenterExplore.onScrollState(newState, firstVisibleItemPosition, offset);
    }

    @Override
    protected void onSwipeToRefresh() {
        presenterExplore.onSwipeRefresh();
    }

    private class ListenerPresenter implements IPresenterExploreListener {


    }
}
