package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterChatListener;

public interface IPresenterChat {
    void onCreateView();

    void setListener(IPresenterChatListener listener);

    void onClickSmileShy();

    void onClickSmileLove();

    void onClickSmileKiss();

    void onClickSend(String message);

    void onClickSmileHeart();
}
