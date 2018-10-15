package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.R;
import com.ringoid.view.PAGE_ENUM;

public class FragmentMatches extends FragmentFeedPage {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container);
        initViews(view);
        showEmpty(R.string.message_empty_matches);
        return view;
    }

    @Override
    public PAGE_ENUM getPage() {
        return PAGE_ENUM.FEED_MATCHES;
    }

    @Override
    protected void onScrollState(int newState, int firstVisibleItemPosition, int offset) {

    }

    @Override
    protected void onSwipeToRefresh() {

    }

}
