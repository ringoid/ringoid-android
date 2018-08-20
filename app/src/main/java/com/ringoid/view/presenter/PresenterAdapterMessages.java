/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterMessages implements IPresenterAdapterMessages {

    @Inject
    INavigator navigator;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    IViewPopup viewPopup;

    public PresenterAdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheMessages.getItemsNum();
    }

    @Override
    public int getItemsNum(int position) {
        return cacheMessages.getItemsNum(position);
    }

    @Override
    public boolean isMessagesNew(int position) {
        return position == 1;
    }

    @Override
    public boolean isMessagesExist(int position) {
        return position == 2;
    }

    @Override
    public void onClickScrolls() {
        viewPopup.showToast(R.string.message_scroll_help);
    }
}
