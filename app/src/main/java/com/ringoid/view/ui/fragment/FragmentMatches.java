package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.R;

public class FragmentMatches extends FragmentInnerTab {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container);
        initViews(view);
        rvItems.setScrollBarColor(getContext().getResources().getColor(R.color.app_red_dark));
        showEmpty(R.string.message_empty_matches);
        return view;
    }

    @Override
    protected void onShow(int state) {

    }

    @Override
    protected void onScrollState(int newState, int firstVisibleItemPosition, int offset) {

    }

    @Override
    protected void onSwipeToRefresh() {

    }

}
