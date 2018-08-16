/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

public interface IPresenterAdapterMessages {
    int getItemsNum();

    int getItemsNum(int position);

    void onClickItem(int position);

    boolean isMessagesNew(int position);

    boolean isMessagesExist(int position);
}