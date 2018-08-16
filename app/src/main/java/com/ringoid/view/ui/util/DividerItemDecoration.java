/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ringoid.R;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public DividerItemDecoration(Context context) {
        margin = (int) context.getResources().getDimension(R.dimen.divider);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildLayoutPosition(view);

        if (position != 0)
            outRect.top = margin;
    }

}
