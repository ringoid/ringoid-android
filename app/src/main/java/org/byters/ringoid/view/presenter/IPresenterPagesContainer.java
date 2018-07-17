package org.byters.ringoid.view.presenter;

import android.support.v4.app.FragmentManager;

import org.byters.ringoid.view.presenter.callback.IPresenterPagesContainerListener;

public interface IPresenterPagesContainer {
    void onViewCreate(FragmentManager childFragmentManager, int viewId);

    void onClickWallet();

    void onClickPageRank();

    void onClickPageLikes();

    void onClickPageProfile();

    void onClickPageMessages();

    void onClickPageExplore();

    void setListener(IPresenterPagesContainerListener listener);
}
