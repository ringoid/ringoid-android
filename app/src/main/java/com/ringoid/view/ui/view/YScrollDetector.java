package com.ringoid.view.ui.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.GestureDetector;
import android.view.MotionEvent;

// Return false if we're scrolling in the x direction
class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        try {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // nothing
        }
        return false;
    }
}
