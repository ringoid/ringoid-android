package org.byters.ringoid.controller.data.memorycache;

public interface ICacheLikes {

    int getItemsNum();

    int getItemsNum(int adapterPosition);

    void setLiked(int adapterPosition, int itemPosition);

    boolean isLiked(int adapterPosition, int itemPosition);

    String getUrl(int adapterPosition, int itemPosition);
}
