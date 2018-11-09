package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.view.PAGE_ENUM;

public interface ICacheInterfaceState {
    void setProfileOriginPhotoId(String originPhotoId);

    String getOriginPhotoId();

    void resetOriginPhotoId();

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

    void resetCachePositionExplore();

    void setPositionScrollSettings(int firsVisibleItemPosition, int offset);

    int getPositionScrollPageSettings();

    int getPositionScrollPageSettingsOffset();

    void setDialogComposeShowState(boolean isShown);

    boolean isDialogComposeShown();

    void updateTheme();

    int getTheme();

    PAGE_ENUM getPageLikes();

    void setPageLikes(PAGE_ENUM page);
}
