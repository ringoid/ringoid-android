/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterAdapterExploreListener;

public interface IPresenterAdapterExplore {
    int getItemsNum();

    int getItemsNum(int position);

    void onClickScrolls();

    void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);

    void setListener(IPresenterAdapterExploreListener listener);
}
