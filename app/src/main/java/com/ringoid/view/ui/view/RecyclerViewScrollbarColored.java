package com.ringoid.view.ui.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.ringoid.R;

public class RecyclerViewScrollbarColored extends RecyclerView {
    private int scrollBarColor;
    private GestureDetector gestureDetector;

    public RecyclerViewScrollbarColored(Context context) {
        super(context);
        init(context);
    }

    public RecyclerViewScrollbarColored(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerViewScrollbarColored(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context cx) {
        scrollBarColor = getResources().getColor(R.color.grey_middle);
        initGestureDetector(cx);
    }

    private void initGestureDetector(Context cx) {
        gestureDetector = new GestureDetector(cx, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public void setScrollBarColor(@ColorInt int scrollBarColor) {
        this.scrollBarColor = scrollBarColor;
    }

    protected void onDrawHorizontalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setColorFilter(scrollBarColor, PorterDuff.Mode.SRC_ATOP);
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    /**
     * Called by Android {@link android.view.View#onDrawScrollBars(Canvas)}
     **/
    protected void onDrawVerticalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setColorFilter(scrollBarColor, PorterDuff.Mode.SRC_ATOP);
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Call super first because it does some hidden motion event handling
        boolean result = super.onInterceptTouchEvent(ev);
        //Now see if we are scrolling vertically with the custom gesture detector

        if (gestureDetector.onTouchEvent(ev)) {
            return result;
        }
        //If not scrolling vertically (more y than x), don't hijack the event.
        else {
            return false;
        }
    }

}
