package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.Window;

public interface IHelperScreenshots {
    void init(Window window);

    void changeStateScreenshots(Window window);

    boolean isScreenshotsSecured(Window window);
}
