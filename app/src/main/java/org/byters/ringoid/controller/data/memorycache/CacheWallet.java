package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheWalletListener;

import java.lang.ref.WeakReference;

public class CacheWallet implements ICacheWallet {

    private int coins;

    private WeakReference<ICacheWalletListener> refListener;

    @Override
    public void setListener(ICacheWalletListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public int getCoinsNum() {
        return coins;
    }
}
