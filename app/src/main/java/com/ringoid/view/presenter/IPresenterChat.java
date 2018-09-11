/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterChatListener;

public interface IPresenterChat {
    void onCreateView();

    void setListener(IPresenterChatListener listener);

    void onClickSend(String message);

    void onClickClear();
}
