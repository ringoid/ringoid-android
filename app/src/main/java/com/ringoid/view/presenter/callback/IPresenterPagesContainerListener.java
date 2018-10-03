/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {

    void setPosition(int pos);

    void setPageSelected(int num, int iconResProfile, int iconResLikes, int iconResMatches, int iconResExplore);

    void showAnimationLike();

    void showAnimationMessage();

    void showAnimationMatches();
}
