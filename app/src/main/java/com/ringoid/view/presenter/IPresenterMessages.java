/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterMessagesListener;

public interface IPresenterMessages {
    void onScroll(int dy);

    void onScrollState(int newState, int firstVisibleItemPosition);

    void setListener(IPresenterMessagesListener listener);

    void onCreateView();

    boolean isPositionTop();

    void scrollTop();
}
