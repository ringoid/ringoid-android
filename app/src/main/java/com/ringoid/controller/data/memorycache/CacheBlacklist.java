/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheBlacklistListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataBlacklistPhone;
import com.ringoid.model.DataBlacklistPhones;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class CacheBlacklist implements ICacheBlacklist {

    @Inject
    ICacheStorage cacheStorage;
    private DataBlacklistPhones data;
    private WeakReference<ICacheBlacklistListener> refListener;

    public CacheBlacklist() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void addPhone(String code, String phone) {
        if (isContains(code, phone)) return;

        DataBlacklistPhones data = getData();
        if (data == null) this.data = new DataBlacklistPhones();
        this.data.add(new DataBlacklistPhone(code, phone));
        saveData();
        notifyListeners();
    }

    private DataBlacklistPhones getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.BLACKLIST, DataBlacklistPhones.class);
        return data;
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.BLACKLIST, data);
    }

    private boolean isContains(String code, String phone) {
        DataBlacklistPhones data = getData();
        if (data == null) return false;
        return data.isContains(code, phone);
    }

    private void notifyListeners() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdate();
    }

    @Override
    public void setListener(ICacheBlacklistListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public int getItemsNum() {
        DataBlacklistPhones data = getData();
        return data == null ? 0 : data.size();
    }

    @Override
    public DataBlacklistPhone getItem(int position) {
        DataBlacklistPhones data = getData();
        return data == null ? null : data.get(position);
    }

    @Override
    public void remove(int position) {
        DataBlacklistPhones data = getData();
        if (data == null) return;
        data.remove(position);
        saveData();
        notifyListeners();
    }

}
