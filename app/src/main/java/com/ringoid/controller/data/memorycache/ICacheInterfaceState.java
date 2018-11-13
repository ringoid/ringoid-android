package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.view.PAGE_ENUM;

public interface ICacheInterfaceState {
    void setPhotoSelected(String originPhotoId, String photoLocalId);

    String getOriginPhotoId();

    void resetCache();

    void resetCurrentPage();

    void addListener(ICacheInterfaceStateListener listener);

    PAGE_ENUM getCurrentPage();

    void setCurrentPage(PAGE_ENUM page);

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

    void resetCachePositionMessages();

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

    void resetCachePositionMatches();

    void setPhoneCode(String s);

    void setPhone(String s);

    void resetCacheSavedPhone();

    boolean isPhoneExist();

    int getPhoneCode();

    String getPhone();

    void setThemeDark(boolean isChecked);

    String getPhotoLocalId();

    void updatePhotoSelected(String photoClientId, String photoOriginId);

    void resetPhotoSelected();
}
