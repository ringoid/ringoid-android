package org.byters.ringoid.controller.data.memorycache;

public interface ICacheProfile {
    int getItemsNum();

    float getItemRatio(int position);

    int getLikesNum(int position);

    String getImage(int pos);
}
