package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheMessages;
import org.byters.ringoid.view.INavigator;

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
        navigator.navigateChat();
    }
}
