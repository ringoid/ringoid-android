/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.util;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.ringoid.view.ui.util.listener.IScrollCompleteCallback;

import java.lang.ref.WeakReference;

public class ListenerScrollComplete extends RecyclerView.OnScrollListener {

    private WeakReference<IScrollCompleteCallback> refListener;

    public ListenerScrollComplete(IScrollCompleteCallback listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState != RecyclerView.SCROLL_STATE_IDLE)
            return;

        if (refListener == null || refListener.get() == null) return;
        refListener.get().onScrollComplete();
    }

}
