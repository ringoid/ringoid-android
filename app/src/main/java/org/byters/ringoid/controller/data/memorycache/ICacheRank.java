package org.byters.ringoid.controller.data.memorycache;

public interface ICacheRank {
    int getItemsNum();

    float getItemRatio(int adapterPosition, int itemPos);

    int getItemsNum(int position);

    String getImage(int position, int imagePosition);
}
