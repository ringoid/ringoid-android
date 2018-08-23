/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

package com.ringoid.controller.data.memorycache;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.DataUser;

import javax.inject.Inject;

public class CacheUser implements ICacheUser {

    @Inject
    ICacheStorage cacheStorage;

    private DataUser data;

    public CacheUser() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setPhone(String code, String phone) {

        data = new DataUser(code, phone);
        cacheStorage.writeData(FileEnum.USER, data);
    }

    @Override
    public String getPhoneCode() {
        DataUser data = getData();
        return data == null ? null : data.getCode();
    }

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.USER);
    }

    private DataUser getData() {
        if (data == null) data = cacheStorage.readObject(FileEnum.USER, DataUser.class);
        return data;
    }
}
