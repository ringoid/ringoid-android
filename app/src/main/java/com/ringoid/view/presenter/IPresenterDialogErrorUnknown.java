package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.view.presenter.callback.IPresenterDialogErrorUnknownListener;

public interface IPresenterDialogErrorUnknown {
    void setListener(IPresenterDialogErrorUnknownListener listener);

    void onShow(Context context);
}
