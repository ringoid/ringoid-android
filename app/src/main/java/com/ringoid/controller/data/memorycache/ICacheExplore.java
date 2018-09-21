/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheExplore {
    int getItemsNum();

    int getItemsNum(int adapterPosition);

    void setLiked(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);

    void changeLiked(int adapterPosition, int itemPosition);

    String getItemId(int adapterPosition, int itemPosition);

    void setSelected(int adapterPosition, int firstVisibleItemPosition);

    int getSelectedPhotoPosition(int position);
}
