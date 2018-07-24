package org.byters.ringoid.view.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.controller.data.memorycache.ICacheScroll;
import org.byters.ringoid.controller.data.memorycache.ICacheWallet;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheScrollListener;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheWalletListener;
import org.byters.ringoid.controller.data.repository.IRepositoryWallet;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.INavigatorPages;
import org.byters.ringoid.view.presenter.callback.IPresenterPagesContainerListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterPagesContainer implements IPresenterPagesContainer {

    @Inject
    INavigatorPages navigatorPages;

    @Inject
    INavigator navigator;

    @Inject
    IRepositoryWallet repositoryWallet;

    @Inject
    ICacheWallet cacheWallet;

    @Inject
    ICacheScroll cacheScroll;

    private ListenerCacheScroll listenerCacheScroll;
    private CacheWalletListener listenerCacheWallet;
    private WeakReference<IPresenterPagesContainerListener> refListener;


    public PresenterPagesContainer() {
        ApplicationRingoid.getComponent().inject(this);
        cacheWallet.setListener(listenerCacheWallet = new CacheWalletListener());
        cacheScroll.setListener(listenerCacheScroll = new ListenerCacheScroll());
    }

    @Override
    public void onViewCreate(FragmentManager childFragmentManager, int viewId) {
        navigatorPages.set(childFragmentManager, viewId);
        navigatorPages.navigateExplore();
        repositoryWallet.request();
    }

    @Override
    public void onClickWallet() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showDialogInvite();
    }

    @Override
    public void onClickPageRank() {
        navigatorPages.navigateRank();
    }

    @Override
    public void onClickPageLikes() {
        navigatorPages.navigateLikes();
    }

    @Override
    public void onClickPageProfile() {
        navigatorPages.navigateProfile();
    }

    @Override
    public void onClickPageMessages() {
        navigatorPages.navigateMessages();
    }

    @Override
    public void onClickPageExplore() {
        navigatorPages.navigateExplore();
    }

    @Override
    public void setListener(IPresenterPagesContainerListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickSettings() {
        navigator.navigateSettings();
    }

    private void updateWallet(int coinsNum) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setWallet(coinsNum);
    }

    private class CacheWalletListener implements ICacheWalletListener {
        @Override
        public void onUpdate() {
            updateWallet(cacheWallet.getCoinsNum());
        }
    }

    private class ListenerCacheScroll implements ICacheScrollListener {
        @Override
        public void onScroll(int scrollSum) {
            if (refListener == null || refListener.get() == null) return;

            refListener.get().setPosition(-scrollSum, scrollSum);

        }
    }
}
