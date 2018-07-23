package org.byters.ringoid.view.ui.util;


import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.byters.ringoid.R;

public class DotsIndicator {
    private final FrameLayout flIndicator;

    private LinearLayout llDots;
    private int savedPos;

    DotsIndicator(FrameLayout flIndicator) {
        this.flIndicator = flIndicator;
        savedPos = -1;
    }

    public void init() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) flIndicator.getResources().getDimension(R.dimen.indicator_padding_horizontal);
        params.rightMargin = (int) flIndicator.getResources().getDimension(R.dimen.indicator_padding_horizontal);
        llDots = new LinearLayout(flIndicator.getContext());
        llDots.setLayoutParams(params);
        this.flIndicator.addView(llDots);
    }

    public void initDots() {
        savedPos = -1;
        updateDots(0);
    }

    private void updateDots(int pos) {
        if (savedPos == pos) return;
        savedPos = pos;
        for (int i = 0; i < llDots.getChildCount(); ++i)
            llDots.getChildAt(i).setBackgroundResource(i == pos ? R.drawable.dot_accent : R.drawable.dot_grey);
    }

    public void setDots(int itemCount) {
        if (itemCount <= 0)
            return;

        llDots.removeAllViews();

        int margin = (int) llDots.getContext().getResources().getDimension(R.dimen.dot_photo_list_margin);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.setMargins(0, margin, 0, margin);

        for (int i = 0; i < itemCount; ++i) {

            FrameLayout view = new FrameLayout(llDots.getContext());
            view.setBackgroundResource(R.drawable.dot_accent);
            view.setLayoutParams(params);
            llDots.addView(view);
        }
    }

    public void setVisibility(int visibility) {
        flIndicator.setVisibility(visibility);
    }

    void onScroll(int pos, int right) {
        if (right < llDots.getContext().getResources().getDisplayMetrics().widthPixels / 2)
            pos += 1;
        updateDots(pos);
    }

    @Deprecated
    public void onScroll(int pos, View v) {
        onScroll(pos, v.getRight());
    }
}
