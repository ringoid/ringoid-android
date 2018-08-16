/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterChatListener {
    void setImage(String url);

    void setDataExist(boolean exist);

    void setSendEnabled(boolean enabled);
}
