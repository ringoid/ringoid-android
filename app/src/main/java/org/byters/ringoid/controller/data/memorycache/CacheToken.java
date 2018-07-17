package org.byters.ringoid.controller.data.memorycache;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.FileEnum;
import org.byters.ringoid.controller.device.ICacheStorage;

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

    private String getToken() {
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
}
