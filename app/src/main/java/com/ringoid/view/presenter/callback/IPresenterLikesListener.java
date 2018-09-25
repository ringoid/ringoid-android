package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterLikesListener {
    boolean isPositionTop();

    void scrollTop();

    void onLike(int adapterPosition);

    void onUnlike(int adapterPosition);

    void scrollToPosition(int position);

    void completeRefresh();
}
