package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterChatListener;

public interface IPresenterChat {
    void onCreateView();

    void setListener(IPresenterChatListener listener);

    void onClickSmileWink();

    void onClickSmileLove();

    void onClickSmileKiss();

    void onClickSend(String message);
}
