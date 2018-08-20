/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterExplore implements IPresenterAdapterExplore {

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    IViewPopup viewPopup;

    public PresenterAdapterExplore() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheExplore.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheExplore.getItemsNum(position);
    }

    @Override
    public void onClickScrolls() {
        viewPopup.showToast(R.string.message_scroll_help);
    }
}
