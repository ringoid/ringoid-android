/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.data.memorycache.listener.ICacheMessagesListener;
import com.ringoid.view.presenter.callback.IPresenterAdapterMessagesListener;
import com.ringoid.view.ui.util.IHelperMessageCompose;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterMessages implements IPresenterAdapterMessages {


    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    IHelperMessageCompose helperMessageCompose;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private ListenerCacheChatMessages listenerCacheChatMessages;
    private ICacheInterfaceStateListener listenerCacheInterfaceState;
    private ICacheMessagesListener listenerCacheMessages;
    private WeakReference<IPresenterAdapterMessagesListener> refListener;

    public PresenterAdapterMessages() {
        ApplicationRingoid.getComponent().inject(this);
        cacheMessages.addListener(listenerCacheMessages = new ListenerCacheMessages());
        cacheChatMessages.addListener(listenerCacheChatMessages = new ListenerCacheChatMessages());
        cacheInterfaceState.addListener(listenerCacheInterfaceState = new ListenerCacheInterfaceState());
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
        return cacheChatMessages.isMessageNew(cacheMessages.getUserId(position));
    }

    @Override
    public boolean isMessagesExist(int position) {
        return cacheChatMessages.isDataExist(cacheMessages.getUserId(position));
    }

    @Override
    public void setListener(IPresenterAdapterMessagesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public boolean isChatEmpty(int position) {
        return !cacheChatMessages.isDataExist(cacheMessages.getUserId(position));
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        return cacheMessages.isLikedAnyPhoto(position);
    }

    @Override
    public void onClickChat(int position) {
        helperMessageCompose.onClick(cacheMessages.getUser(position));
    }

    @Override
    public void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheMessages.setSelected(adapterPosition, firstVisibleItemPosition);
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return cacheMessages.getSelectedPhotoPosition(position);
    }

    @Override
    public boolean isControlsVisible() {
        return !cacheInterfaceState.isDialogComposeShown();
    }

    private class ListenerCacheMessages implements ICacheMessagesListener {
        @Override
        public void onUpdate() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }

        @Override
        public void onSelectedUserUpdate(String userSelectedID) {
        }
    }

    private class ListenerCacheChatMessages implements ICacheChatMessagesListener {
        @Override
        public void onDataChange() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }

    private class ListenerCacheInterfaceState implements ICacheInterfaceStateListener {
        @Override
        public void onPageSelected(int num) {

        }

        @Override
        public void onDialogComposeShowState(boolean isShown) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }

        @Override
        public void onThemeUpdate() {

        }
    }
}
