package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterMessagesListener {
    void scrollToTop();

    void scrollToPosition(int positionScrollPageMessages);

    boolean isPositionTop();

    void completeRefresh();
}
