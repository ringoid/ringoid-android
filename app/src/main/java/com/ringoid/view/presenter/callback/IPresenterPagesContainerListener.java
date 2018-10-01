/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {

    void setPosition(int pos);

    void setPageSelected(int num);

    void setBottomSheetDrawables(int likes, int messages, int explore);

    void showAnimationLike();

    void showAnimationMessage();

    void showAnimationMatches();
}
