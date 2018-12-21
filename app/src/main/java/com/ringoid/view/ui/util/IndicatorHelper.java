/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.util;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.ringoid.R;

public class IndicatorHelper {
    private IIndicator indicator;
    private LinearLayoutManager layoutManager;

    private IndicatorHelper(FrameLayout viewContainer, RecyclerView recyclerView, LinearLayoutManager layoutManager, IIndicator indicator) {
        this.indicator = indicator;
        indicator.init(viewContainer);
        recyclerView.addOnScrollListener(new ScrollListener());
        this.layoutManager = layoutManager;
    }

    public static IndicatorHelper getLinesHelper(FrameLayout viewContainer, RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        return new IndicatorHelper(viewContainer,
                recyclerView,
                layoutManager,
                new LinesIndicator(R.drawable.indicator_line_white, R.drawable.indicator_line_grey));
    }


    public static IndicatorHelper getLinesAccentGreenHelper(FrameLayout viewContainer, RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        return new IndicatorHelper(viewContainer,
                recyclerView,
                layoutManager,
                new LinesIndicator(R.drawable.indicator_line_accent_green, R.drawable.indicator_line_grey));
    }

    public static IndicatorHelper getLinesPinkHelper(FrameLayout viewContainer, RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        return new IndicatorHelper(viewContainer,
                recyclerView,
                layoutManager,
                new LinesIndicator(R.drawable.indicator_line_pink, R.drawable.indicator_line_grey));
    }

    public void updateData(int num) {
        indicator.setDots(num);
        indicator.initDots(layoutManager.findFirstVisibleItemPosition());
    }

    public void setPosition(int position) {
        indicator.setPosition(position);
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int pos = layoutManager.findFirstVisibleItemPosition();

            indicator.onScroll(pos, layoutManager.findViewByPosition(pos).getRight());
        }
    }
}
