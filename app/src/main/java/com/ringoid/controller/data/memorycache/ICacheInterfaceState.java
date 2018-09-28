package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;

public interface ICacheInterfaceState {
    void setProfileOriginPhotoId(String originPhotoId);

    String getOriginPhotoId();

    void resetCache();

    void resetCurrentPage();

    void addListener(ICacheInterfaceStateListener listener);

    int getCurrentPage();

    void setCurrentPage(int i);

    int getPositionScrollPageLikes();

    void setPositionScrollPageLikes(int position, int offset);

    int getPositionScrollPageExplore();

    int getPositionScrollPageMessages();

    void setPositionScrollPageMessages(int firstVisibleItemPosition, int offset);

    void setPositionScrollPageExplore(int firstVisibleItemPosition, int offset);


    int getPositionScrollPageExploreOffset();

    int getPositionScrollPageLikesOffset();

    int getPositionScrollPageMessagesOffset();

    void resetCachePositionLikes();

    void resetCachePositionMessage();

    void resetCachePositionExplore();
}
