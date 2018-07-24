package org.byters.ringoid.view.presenter;

import android.content.Context;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheExplore;

import javax.inject.Inject;

public class PresenterAdapterExplore implements IPresenterAdapterExplore {

    @Inject
    ICacheExplore cacheExplore;

    public PresenterAdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheExplore.getItemsNum();
    }

    @Override
    public int getItemHeight(Context context, int position) {
        float ratio = cacheExplore.getItemRatioMax(position);
        return (int) (ratio * context.getResources().getDisplayMetrics().widthPixels);
    }

    @Override
    public int getItemsNum(int position) {
        return cacheExplore.getItemsNum(position);
    }
}
