/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheProfile {
    int getItemsNum();

    int getLikesNum(int position);

    String getImage(int pos);
}
