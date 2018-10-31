/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterChatListener {
    void setMessageCompose(String message);

    void scrollToEnd();

    void finishView();

    void scrollToMessage(int position, int offset);
}
