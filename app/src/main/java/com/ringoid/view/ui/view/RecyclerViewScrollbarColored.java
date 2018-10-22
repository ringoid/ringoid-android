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

import com.ringoid.R;

public class RecyclerViewScrollbarColored extends RecyclerView {
    private int scrollBarColor;

    public RecyclerViewScrollbarColored(Context context) {
        super(context);
        init();
    }

    public RecyclerViewScrollbarColored(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewScrollbarColored(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        scrollBarColor = getResources().getColor(R.color.app_button_material_light);
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
}
