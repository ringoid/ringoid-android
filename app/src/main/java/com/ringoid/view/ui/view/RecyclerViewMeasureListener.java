package com.ringoid.view.ui.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ringoid.view.ui.view.callback.IMeasureListener;

import java.lang.ref.WeakReference;

public class RecyclerViewMeasureListener extends RecyclerView {
    private WeakReference<IMeasureListener> refListener;

    public RecyclerViewMeasureListener(Context context) {
        super(context);
    }

    public RecyclerViewMeasureListener(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewMeasureListener(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        notifyListeners();
    }

    private void notifyListeners() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdate();
    }

    public void setListener(IMeasureListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
