/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.util;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ringoid.R;

public class LinesIndicator implements IIndicator {
    private final int resAccent, resDefault;
    private FrameLayout flIndicator;

    private LinearLayout llDots;
    private int savedPos;

    public LinesIndicator(int resAccent, int resDefault) {
        this.resAccent = resAccent;
        this.resDefault = resDefault;
    }

    @Override
    public void initDots(int pos) {
        savedPos = -1;
        updateDots(pos);
    }

    private void updateDots(final int pos) {
        if(savedPos==pos) return;
        savedPos = pos;

        flIndicator.post(new Runnable() {
            @Override
            public void run() {
                UpdateDotsInternal(pos);
            }
        });

    }

    private void UpdateDotsInternal(int pos) {
        for (int i = 0; i < llDots.getChildCount(); ++i) {
            llDots.getChildAt(i).setBackgroundResource(i == pos ? resAccent : resDefault);
            llDots.setGravity(Gravity.CENTER);

            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)llDots.getChildAt(i).getLayoutParams();
            if (i == pos) {
                params.width  = (int) (llDots.getContext().getResources().getDisplayMetrics().density * 15);
                params.height = (int) (llDots.getContext().getResources().getDisplayMetrics().density * 15);
            } else {
                params.width  = (int) (llDots.getContext().getResources().getDisplayMetrics().density * 10);
                params.height = (int) (llDots.getContext().getResources().getDisplayMetrics().density * 10);
            }

            llDots.getChildAt(i).setLayoutParams(params);

        }
    }

    @Override
    public void init(FrameLayout layout) {

        this.flIndicator = layout;
        savedPos = -1;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) flIndicator.getResources().getDimension(R.dimen.indicator_padding_horizontal);
        params.rightMargin = (int) flIndicator.getResources().getDimension(R.dimen.indicator_padding_horizontal);
        llDots = new LinearLayout(flIndicator.getContext());
        llDots.setLayoutParams(params);
        this.flIndicator.addView(llDots);
    }

    @Override
    public void setDots(int itemCount) {
        if (itemCount <= 0)
            return;

        llDots.removeAllViews();

        int margin   = (int) llDots.getContext().getResources().getDimension(R.dimen.dot_photo_list_margin);
        int marginLeftRight = (int) (flIndicator.getResources().getDisplayMetrics().density * 2);
        for (int i = 0; i < itemCount; ++i) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (llDots.getContext().getResources().getDisplayMetrics().density * 10),
                    (int) (llDots.getContext().getResources().getDisplayMetrics().density * 10));
            params.gravity = Gravity.CENTER;
            params.weight = 1;
            params.setMargins(marginLeftRight, margin, marginLeftRight, margin);

            FrameLayout view = new FrameLayout(llDots.getContext());
            view.setBackgroundResource(R.drawable.indicator_line_white);
            view.setLayoutParams(params);
            llDots.addView(view);
        }
    }

    public void setVisibility(int visibility) {
        flIndicator.setVisibility(visibility);
    }

    @Override
    public void onScroll(int pos, int right) {
        if (right < llDots.getContext().getResources().getDisplayMetrics().widthPixels / 2)
            pos += 1;
        updateDots(pos);
    }

    @Override
    public void setPosition(int position) {
        updateDots(position);
    }
}
