package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.Window;

public interface IHelperScreenshots {

    void enableScreenshots();

    void disableScreenshots();

    void changeStateScreenshots();

    boolean isScreenshotsSecured();

    void set(Window window);
}
