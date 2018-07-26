package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheMessages;
import org.byters.ringoid.view.INavigator;

import javax.inject.Inject;

public class PresenterAdapterMessages implements IPresenterAdapterMessages {

    @Inject
    INavigator navigator;

    @Inject
    ICacheMessages cacheMessages;

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
    public void onClickItem(int position) {
        navigator.navigateChat();
    }
}
