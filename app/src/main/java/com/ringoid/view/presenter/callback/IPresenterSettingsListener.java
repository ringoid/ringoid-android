package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterSettingsListener {

    void scrollToPosition(int firstVisiblePosition, int offset);

    boolean isPositionTop();
}
