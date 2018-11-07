package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheMessageCompose;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterChatListener;
import com.ringoid.view.ui.util.IHelperMessageSend;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterChat implements IPresenterChat {
    @Inject
    IViewPopup viewPopup;

    @Inject
    IHelperMessageSend helperMessageSend;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheMessageCompose cacheMessageCompose;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    IViewDialogs viewDialogs;

    private WeakReference<IPresenterChatListener> refListener;

    public PresenterChat() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onDialogCreate() {
        setMessageComposeSaved();
    }

    @Override
    public void setListener(IPresenterChatListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickMessageSend(String message) {
        boolean messagesExist = cacheChatMessages.isDataExist(cacheMessages.getUserSelectedID());
        helperMessageSend.sendMessage(cacheMessages.getUserSelectedID(), message);

        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToEnd();

        if (!messagesExist) {
            viewPopup.showToast(R.string.message_sent);
            refListener.get().finishView();
        }
    }

    @Override
    public void onDialogDismiss(String message) {
        cacheMessageCompose.setMessage(message);
        cacheInterfaceState.setDialogComposeShowState(false);
    }

    @Override
    public void onDialogShow() {
        cacheInterfaceState.setDialogComposeShowState(true);
    }

    @Override
    public void onListInited() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToMessage(cacheChatMessages.getScrollSavedPosition(cacheMessages.getUserSelectedID()),
                cacheChatMessages.getScrollSavedPositionOffset(cacheMessages.getUserSelectedID()));
    }

    @Override
    public void onSCroll(int newState, int pos, int offset) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheChatMessages.setScrollSavedPosition(cacheMessages.getUserSelectedID(), pos, offset);
    }

    @Override
    public void onClickReport() {
        viewDialogs.showDialogReport();
    }

    private void setMessageComposeSaved() {
        String message = cacheMessageCompose.getMessage();
        if (TextUtils.isEmpty(message)) return;

        if (refListener != null && refListener.get() != null)
            refListener.get().setMessageCompose(message);
        cacheMessageCompose.resetCache();

    }
}
