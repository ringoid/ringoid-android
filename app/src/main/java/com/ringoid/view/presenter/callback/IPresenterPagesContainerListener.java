/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {

    void setPosition(boolean isDown);

    void setPageSelected(int num);

    void setBottomSheetDrawables(int profile, int likes, int messages, int explore);

    void setStatusBarColor(int type);

    void showAnimationLike();

    void showAnimationMessage();

    void showAnimationMatches();
}
