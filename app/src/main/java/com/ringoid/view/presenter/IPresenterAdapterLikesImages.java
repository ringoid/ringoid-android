/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

public interface IPresenterAdapterLikesImages {
    int getItemsNum(int adapterPosition);

    void onClickLike(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);

    void onLongClick(int adapterPosition, int itemPosition);

    boolean onClickIconLike(int adapterPosition, int itemPosition);

    boolean isControlsVisible();
}
