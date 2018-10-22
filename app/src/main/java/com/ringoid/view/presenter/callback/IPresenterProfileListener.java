package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterProfileListener {
    void updateView(int position);

    void refreshComplete();

    void scrollToPosition(int pos);
}
