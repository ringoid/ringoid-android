package org.byters.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {
    void showDialogInvite();

    void setWallet(int coinsNum);

    void setPosition(int topPos, int bottomPos);

    void setPageSelected(int num);
}
