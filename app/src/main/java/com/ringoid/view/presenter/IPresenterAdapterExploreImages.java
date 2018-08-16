package com.ringoid.view.presenter;

public interface IPresenterAdapterExploreImages {
    int getItemsNum(int adapterPosition);

    void onClickLike(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);
}
