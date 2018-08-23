/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheLikes {

    int getItemsNum();

    int getItemsNum(int adapterPosition);

    void setLiked(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);

    boolean isDataExist();

    void changeLiked(int adapterPosition, int itemPosition);

    String getItemId(int adapterPosition, int itemPosition);
}
