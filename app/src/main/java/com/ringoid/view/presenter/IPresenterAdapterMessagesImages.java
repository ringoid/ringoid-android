package com.ringoid.view.presenter;

public interface IPresenterAdapterMessagesImages {
    int getItemsNum(int adapterPosition);

    String getUrl(int adapterPosition, int itemPosition);

    void onClickItem(int position);
}
