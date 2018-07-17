package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheWalletListener;

public interface ICacheWallet {
    void setListener(ICacheWalletListener listener);

    int getCoinsNum();
}
