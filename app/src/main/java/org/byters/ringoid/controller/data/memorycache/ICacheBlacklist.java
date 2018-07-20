package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheBlacklistListener;
import org.byters.ringoid.model.DataBlacklistPhone;

public interface ICacheBlacklist {
    void addPhone(String phone);

    void setListener(ICacheBlacklistListener iCacheBlacklistListener);

    int getItemsNum();

    DataBlacklistPhone getItem(int position);

    void remove(int position);

    void changeSelect(int position);

    boolean isSelected(int position);
}
