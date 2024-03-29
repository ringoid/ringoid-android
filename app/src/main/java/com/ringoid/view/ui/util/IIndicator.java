/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.util;

import android.widget.FrameLayout;

interface IIndicator {
    void init(FrameLayout layout);

    void setDots(int num);

    void initDots(int pos);

    void onScroll(int pos, int right);

    void setPosition(int position);
}
