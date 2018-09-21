/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterAdapterLikesListener;

public interface IPresenterAdapterLikes {
    int getItemsNum();

    int getItemsNum(int position);

    void setListener(IPresenterAdapterLikesListener listener);

    void onClickChat(int adapterPosition);

    boolean isLikedAnyPhoto(int position);

    boolean isChatEmpty(int position);

    void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);
}
