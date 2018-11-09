package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v4.app.FragmentManager;

import com.ringoid.view.presenter.callback.IPresenterLikesContainerListener;

public interface IPresenterLikesContainer {
    void onClick(int id);

    void onCreateView(FragmentManager childFragmentManager, int flContentLikes);

    void setListener(IPresenterLikesContainerListener listener);

    void scrollTop();

    void showPageLikes();
}
