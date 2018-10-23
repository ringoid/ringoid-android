/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view;

import android.support.v4.app.FragmentManager;

public interface INavigatorPages {

    void set(FragmentManager childFragmentManager, int viewId);

    void navigateLikes();

    void navigateProfile();

    void navigateMessages();

    void navigateExplore();

    void navigateCurrentPage();

    boolean isPageExplore();

    boolean isPageLikes();

    boolean isPageProfile();

    boolean isPageMessages();

    void navigateMatches();

    void navigateProfilePhotoAdd();
}
