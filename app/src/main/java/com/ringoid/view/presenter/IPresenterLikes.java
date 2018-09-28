/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterLikesListener;

public interface IPresenterLikes {

    void onCreateView();

    void onScrollState(int newState, int firstVisibleItemPosition, int offset);

    void setListener(IPresenterLikesListener listener);

    void onSwipeToRefresh();
}
