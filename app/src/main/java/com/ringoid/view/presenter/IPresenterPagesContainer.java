/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v4.app.FragmentManager;

import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;

public interface IPresenterPagesContainer {
    void onViewCreate(FragmentManager childFragmentManager, int viewId, int action);

    void onClickPageLikes();

    void onClickPageProfile();

    void onClickPageMessages();

    void onClickPageExplore();

    void setListener(IPresenterPagesContainerListener listener);

    void onClickPageMatches();
}
