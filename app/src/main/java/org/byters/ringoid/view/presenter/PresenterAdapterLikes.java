package org.byters.ringoid.view.presenter;

import android.content.Context;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheLikes;

import javax.inject.Inject;

public class PresenterAdapterLikes implements IPresenterAdapterLikes {

    @Inject
    ICacheLikes cacheLikes;

    public PresenterAdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheLikes.getItemsNum();
    }

    @Override
    public int getItemHeight(Context context, int position) {
        float ratio = cacheLikes.getItemRatioMax(position);
        return (int) (ratio * context.getResources().getDisplayMetrics().widthPixels);
    }

    @Override
    public int getItemsNum(int position) {
        return cacheLikes.getItemsNum(position);
    }
}
