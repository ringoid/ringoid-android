package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheScroll;

import javax.inject.Inject;

public class PresenterRank implements IPresenterRank {

    @Inject
    ICacheScroll cacheScroll;

    public PresenterRank(){
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onScroll(int dy) {
        cacheScroll.onScroll(dy);
    }
}
