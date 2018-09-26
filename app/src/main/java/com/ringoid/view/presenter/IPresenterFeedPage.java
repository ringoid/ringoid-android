package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.presenter.callback.IPresenterFeedPageListener;

public interface IPresenterFeedPage{
    void setListener(IPresenterFeedPageListener listener);

    void onScroll(int dy, int scrollSum);
}
