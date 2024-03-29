package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterFeedPageListener {
    void scrollSmoothBy(int y);

    boolean isPositionTop();

    void scrollTop();

    void scrollToPosition(int position, int offset);

    void completeRefresh();

    void showViewNoPhoto(int messageRes);
}
