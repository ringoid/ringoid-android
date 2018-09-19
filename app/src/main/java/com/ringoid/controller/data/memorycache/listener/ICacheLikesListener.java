package com.ringoid.controller.data.memorycache.listener;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface ICacheLikesListener {
    void onUpdate();

    void onLiked(int adapterPosition, int itemPosition);

    void onUnliked(int adapterPosition, int itemPosition);
}
