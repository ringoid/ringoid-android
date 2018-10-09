/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterProfileListener;

public interface IPresenterProfile {
    int getItemsNum();

    void onClickPhotoAdd();

    void onClickToolbar();

    void onCreateView();

    void setListener(IPresenterProfileListener listener);

    void onSwipeRefresh();

    void onShownItem(int pos);
}
