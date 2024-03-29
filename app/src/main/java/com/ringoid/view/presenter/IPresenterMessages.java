/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.view.View;

import com.ringoid.view.presenter.callback.IPresenterMessagesListener;

public interface IPresenterMessages {

    void onScrollState(int newState, int firstVisibleItemPosition, int offset);

    void setListener(IPresenterMessagesListener listener);

    void onCreateView(View view);

    void onSwipeRefresh();
}
