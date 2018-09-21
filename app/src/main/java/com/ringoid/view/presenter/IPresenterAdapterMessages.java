/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterAdapterMessagesListener;

public interface IPresenterAdapterMessages {
    int getItemsNum();

    int getItemsNum(int position);

    boolean isMessagesNew(int position);

    boolean isMessagesExist(int position);

    void setListener(IPresenterAdapterMessagesListener listener);

    boolean isChatEmpty(int position);

    boolean isLikedAnyPhoto(int position);

    void onClickChat(int adapterPosition);

    void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);
}
