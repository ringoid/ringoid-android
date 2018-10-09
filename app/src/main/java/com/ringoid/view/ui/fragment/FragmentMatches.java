package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.PAGE_ENUM;

public class FragmentMatches extends FragmentFeedPage {


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
