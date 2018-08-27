/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;

import javax.inject.Inject;

public class CacheToken implements ICacheToken {

    @Inject
    ICacheStorage cacheStorage;

    private String token;

    public CacheToken() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public boolean isTokenExist() {
        return !TextUtils.isEmpty(getToken());
    }

    @Override
    public String getToken() {
        if (!TextUtils.isEmpty(token))
            return token;

        token = cacheStorage.readObject(FileEnum.TOKEN, String.class);
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
        cacheStorage.writeData(FileEnum.TOKEN, token);
    }

    @Override
    public void resetCache() {
        setToken("");
    }
}
