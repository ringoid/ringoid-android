package org.byters.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {
    void showDialogInvite();

    void setWallet(int coinsNum);

    void setPosition(int topPos, int bottomPos, float alpha);

    void setPageSelected(int num);
}
