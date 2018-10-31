package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.presenter.callback.IPresenterChatListener;

public interface IPresenterChat {
    void onDialogCreate();

    void setListener(IPresenterChatListener listener);

    void onClickMessageSend(String message);

    void onDialogDismiss(String message);

    void onDialogShow();

    void onListInited();

    void onSCroll(int newState, int pos, int offset);
}
