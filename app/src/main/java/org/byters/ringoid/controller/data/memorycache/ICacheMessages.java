package org.byters.ringoid.controller.data.memorycache;

public interface ICacheMessages {
    int getItemsNum();

    int getItemsNum(int adapterPosition);

    String getUrl(int adapterPosition, int itemPosition);

    void setUserSelected(int position);

    String getUrlSelectedUser();

    boolean isDataExist();
}
