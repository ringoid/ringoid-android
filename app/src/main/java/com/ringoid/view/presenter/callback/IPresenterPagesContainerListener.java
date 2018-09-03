/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {

    void setPosition(boolean toolbarScroll, int topPos, int bottomPos, float alpha);

    void showToolbar();

    void setPageSelected(int num);

    void setBottomSheetDrawables(int profile, int likes, int messages, int explore);

    void setStatusBarColor(int type);

    void scrollComplete(boolean toolbarScroll, int scrollSum, int alpha);

    void hideToolbar();
}
