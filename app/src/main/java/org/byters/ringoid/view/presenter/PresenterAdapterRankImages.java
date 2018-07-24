package org.byters.ringoid.view.presenter;

import android.content.Context;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheRank;

import javax.inject.Inject;

public class PresenterAdapterRankImages implements IPresenterAdapterRankImages {

    @Inject
    ICacheRank cacheRank;

    public PresenterAdapterRankImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum(int position) {
        return cacheRank.getItemsNum(position);
    }

    @Override
    public String getUrl(int position, int imagePosition) {
        return "file:///android_asset/" + cacheRank.getImage(position, imagePosition);
    }

    @Override
    public int getItemHeight(Context context, int position, int adapterPosition) {
        float ratio = cacheRank.getItemRatio(position, adapterPosition);
        return (int) (ratio * context.getResources().getDisplayMetrics().widthPixels);
    }
}
