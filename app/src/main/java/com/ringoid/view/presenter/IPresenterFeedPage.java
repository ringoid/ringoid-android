package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.presenter.callback.IPresenterFeedPageListener;
import com.ringoid.view.presenter.callback.IPresenterFeedPagePresenterListener;

public interface IPresenterFeedPage {
    void setListener(IPresenterFeedPageListener listener);

    void onScroll(int dy, int scrollSum);

    boolean isPositionTop();

    void scrollTop();

    boolean checkDataProfileExist(int messageNoDataRes);

    void scrollToPosition(int position, int offset);

    void hideRefreshLayout();

    void addListener(IPresenterFeedPagePresenterListener listener);
}
