/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterLikes implements IPresenterAdapterLikes {

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    IViewPopup viewPopup;

    public PresenterAdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheLikes.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheLikes.getItemsNum(position);
    }

    @Override
    public void onClickScrolls() {
        viewPopup.showToast(R.string.message_scroll_help);
    }
}
