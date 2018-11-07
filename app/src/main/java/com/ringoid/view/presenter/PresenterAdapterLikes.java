/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.support.v7.widget.RecyclerView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.listener.ICacheChatMessagesListener;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.data.memorycache.listener.ICacheLikesListener;
import com.ringoid.view.presenter.callback.IPresenterAdapterLikesListener;
import com.ringoid.view.ui.util.IHelperMessageCompose;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterLikes implements IPresenterAdapterLikes {

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    IHelperMessageCompose helperMessageCompose;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private ListenerCacheInterfaceState listenerCacheInterfaceState;
    private ListenerCacheChatMessages listenerCacheChatMessages;
    private ListenerCacheLikes listenerCacheLikes;
    private WeakReference<IPresenterAdapterLikesListener> refListener;

    public PresenterAdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
        cacheLikes.addListener(listenerCacheLikes = new ListenerCacheLikes());
        cacheChatMessages.addListener(listenerCacheChatMessages = new ListenerCacheChatMessages());
        cacheInterfaceState.addListener(listenerCacheInterfaceState = new ListenerCacheInterfaceState());
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
    public void setListener(IPresenterAdapterLikesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public boolean isChatEmpty(int position) {
        return !cacheChatMessages.isDataExist(cacheLikes.getUserId(position));
    }

    @Override
    public void onScrollPhotoChanged(int newState, int adapterPosition, int firstVisibleItemPosition) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        cacheLikes.setSelected(adapterPosition, firstVisibleItemPosition);
    }

    @Override
    public int getSelectedPhotoPosition(int position) {
        return cacheLikes.getSelectedPhotoPosition(position);
    }

    @Override
    public boolean isControlsVisible() {
        return !cacheInterfaceState.isDialogComposeShown();
    }

    @Override
    public void onClickChat(int position) {
        helperMessageCompose.onClick(cacheLikes.getUser(position));
    }

    @Override
    public boolean isLikedAnyPhoto(int position) {
        return cacheLikes.isLikedAnyPhoto(position);
    }

    private class ListenerCacheLikes implements ICacheLikesListener {
        @Override
        public void onUpdate() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }

        @Override
        public void onLiked(int adapterPosition, int itemPosition) {
        }

        @Override
        public void onUnliked(int adapterPosition, int itemPosition) {

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
