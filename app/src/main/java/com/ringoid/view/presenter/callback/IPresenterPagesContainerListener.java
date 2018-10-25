/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterPagesContainerListener {

    void setPosition(int pos);

    void setPageSelected(int num, int iconResMatches,int iconResProfile, int iconResLikes, int iconResMessages, int iconResExplore);

    void showAnimationLike();

    void showAnimationMessage();

    void showAnimationMatches();

    void setToolbarText(int title, int subtitle);

    void setToolbarShown(boolean isShow);

    void setToolbarSettingsVisibility(boolean isVisible);
}
