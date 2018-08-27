/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.controller.data.memorycache.listener.ICacheBlacklistListener;
import com.ringoid.model.DataBlacklistPhone;

public interface ICacheBlacklist {
    void addPhone(String code, String phone);

    void setListener(ICacheBlacklistListener iCacheBlacklistListener);

    int getItemsNum();

    DataBlacklistPhone getItem(int position);

    void remove(int position);
}
