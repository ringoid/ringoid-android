package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.Window;

public interface IHelperFullscreen {

    void statusbarShowFullscreen();

    void statusbarHide();

    void statusbarShowResizeable();

    void set(Window window);
}
