package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v4.app.FragmentManager;

public interface INavigatorLikes {
    void navigatePage(PAGE_ENUM pageLikes);

    void set(FragmentManager childFragmentManager, int vContent);

    boolean isPageCurrent(PAGE_ENUM pageLikes);
}
