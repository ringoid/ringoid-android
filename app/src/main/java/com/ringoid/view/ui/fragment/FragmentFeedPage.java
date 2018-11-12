package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.ui.view.ViewToolbar;

public abstract class FragmentFeedPage extends FragmentInnerTab {

    View vToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    void initViews(View view) {
        super.initViews(view);
        vToolbar = view.findViewById(R.id.toolbarPages);
        setToolbarText(view);
    }

    @Override
    protected void onShow(int state) {
        if (state == STATE_NO_PHOTO || state == STATE_EMPTY)
            vToolbar.setVisibility(View.VISIBLE);
        else
            vToolbar.setVisibility(View.GONE);
    }

    private void setToolbarText(View view) {
        ((ViewToolbar) view.findViewById(R.id.toolbarPages)).setText(getTitle());
    }

    protected abstract String getTitle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_page, container, false);
    }

    @Override
    RecyclerView.ItemDecoration getItemDecoration() {
        return new ItemDecoration(getContext());
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {

        private int heightBottom;
        private int margin;

        ItemDecoration(Context context) {
            margin = (int) context.getResources().getDimension(R.dimen.divider);

            heightBottom = Math.max(0,
                    getResources().getDisplayMetrics().heightPixels - (int) (getResources().getDisplayMetrics().widthPixels / 3f * 4)
                            - (int) getResources().getDimension(R.dimen.statusbar));

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildLayoutPosition(view);

            if (position > 1)
                outRect.top = margin;
            else
                outRect.top = 0;

            if (position > 1 && position == state.getItemCount() - 1)
                outRect.bottom = heightBottom;
        }

    }

}
