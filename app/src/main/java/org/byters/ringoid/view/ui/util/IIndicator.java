package org.byters.ringoid.view.ui.util;

import android.widget.FrameLayout;

interface IIndicator {
    void init(FrameLayout layout);

    void setDots(int num);

    void initDots();

    void onScroll(int pos, int right);
}
