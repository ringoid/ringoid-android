package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheRank;

import javax.inject.Inject;

public class PresenterAdapterRank implements IPresenterAdapterRank {

    @Inject
    ICacheRank cacheRank;

    public PresenterAdapterRank() {
        ApplicationRingoid.getComponent().inject(this);
    }


    @Override
    public int getItemsNum() {
        return cacheRank.getItemsNum();
    }

    @Override
    public String getRank(int position) {
        return String.valueOf(position + 1);
    }

    @Override
    public int getItemsNum(int position) {
        return cacheRank.getItemsNum(position);
    }
}
