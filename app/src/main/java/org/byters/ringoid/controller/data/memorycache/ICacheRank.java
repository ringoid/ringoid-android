package org.byters.ringoid.controller.data.memorycache;

public interface ICacheRank {
    int getItemsNum();

    int getItemsNum(int position);

    String getImage(int position, int imagePosition);
}
