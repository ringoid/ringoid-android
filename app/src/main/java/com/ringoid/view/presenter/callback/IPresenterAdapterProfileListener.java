package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterAdapterProfileListener {
    void onUpdate();

    void onUpdateRemove(int position);

    void onUpdateAdd(int position);
}
