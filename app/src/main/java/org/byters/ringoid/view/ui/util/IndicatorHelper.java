package org.byters.ringoid.view.ui.util;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

public class IndicatorHelper {
    private IIndicator indicator;
    private LinearLayoutManager layoutManager;

    public IndicatorHelper(FrameLayout viewContainer, RecyclerView recyclerView, LinearLayoutManager layoutManager, IIndicator indicator) {
        this.indicator = indicator;
        indicator.init(viewContainer);
        recyclerView.addOnScrollListener(new ScrollListener());
        this.layoutManager = layoutManager;
    }

    public void updateData(int num) {
        indicator.setDots(num);
        indicator.initDots();
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
