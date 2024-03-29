package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

public interface IHelperTheme {
    int getColor(int res);

    void set(Context context);

    int getDrawableMenuProfileActive();

    int getDrawableMenuProfile();

    int getDrawableMenuLikesActive();

    int getDrawableMenuLikes();

    int getDrawableMenuExploreActive();

    int getDrawableMenuExplore();
}
