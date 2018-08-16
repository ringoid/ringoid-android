/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.view.INavigator;

import javax.inject.Inject;

public class PresenterAdapterMessagesImages implements IPresenterAdapterMessagesImages {

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    INavigator navigator;

    public PresenterAdapterMessagesImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return cacheMessages.getItemsNum(adapterPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return "file:///android_asset/" + cacheMessages.getUrl(adapterPosition, itemPosition);
    }

    @Override
    public void onClickItem(int position) {
        cacheMessages.setUserSelected(position);
        navigator.navigateChat();
    }
}
