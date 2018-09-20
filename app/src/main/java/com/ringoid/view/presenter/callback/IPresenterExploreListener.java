package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterExploreListener {
    boolean isPositionTop();

    void scrollTop();

    void scrollToPosition(int position);
}
