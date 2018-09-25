/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterExploreListener;

public interface IPresenterExplore {
    void onScroll(int dy);

    void onCreateView();

    void onScrollState(int newState, int firstVisibleItemPosition);

    boolean isPositionTop();

    void scrollTop();

    void setListener(IPresenterExploreListener listener);

    void onSwipeRefresh();
}
