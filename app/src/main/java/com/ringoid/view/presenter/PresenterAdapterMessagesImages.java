/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;

import javax.inject.Inject;

public class PresenterAdapterMessagesImages implements IPresenterAdapterMessagesImages {

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    INavigator navigator;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    IViewPopup viewPopup;

    private ListenerDialogChatCompose listenerDialogChatCompose;

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

        boolean isChatEmpty = !cacheChatMessages.isDataExist(cacheMessages.getUserId(position));;

        if (isChatEmpty) {
            viewDialogs.showDialogChatCompose(listenerDialogChatCompose = new ListenerDialogChatCompose(cacheMessages.getUserId(position)));
            return;
        }

        navigator.navigateChat();
    }

    private class ListenerDialogChatCompose implements IDialogChatComposeListener {
        private String userId;

        ListenerDialogChatCompose(String userId) {
            this.userId = userId;
        }

        @Override
        public void onSend(String message) {
            viewPopup.showToast(R.string.message_sent);
            cacheChatMessages.addMessage(userId, message);
        }
    }
}
