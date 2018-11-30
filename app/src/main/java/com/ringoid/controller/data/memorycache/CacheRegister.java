/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelUserRegister;
import com.ringoid.model.SEX;

import javax.inject.Inject;

public class CacheRegister implements ICacheRegister {

    @Inject
    ICacheStorage cacheStorage;

    private ModelUserRegister data;

    public CacheRegister() {
        ApplicationRingoid.getComponent().inject(this);
    }

    private ModelUserRegister getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_USER_REGISTER, ModelUserRegister.class);
        if (data == null) data = new ModelUserRegister();
        return data;
    }

    @Override
    public int getSex() {
        return getData().sex;
    }

    @Override
    public void setSexFemale() {
        getData().sex = SEX.FEMALE.getValue();
        saveData();
    }

    @Override
    public void setSexMale() {
        getData().sex = SEX.MALE.getValue();
        saveData();
    }

    @Override
    public int getYearBirth() {
        return getData().yearOfBirth;
    }

    @Override
    public boolean setDateBirth(int year) {
        if (getData().yearOfBirth == year) return false;
        getData().yearOfBirth = year;
        saveData();
        return true;
    }

    @Override
    public String getSessionId() {
        return getData().sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        getData().sessionId = sessionId;
        saveData();
    }

    @Override
    public long getDateLegal() {
        return getData().dateTerms;
    }

    @Override
    public void setDateLegal() {
        getData().dateTerms = System.currentTimeMillis();
        saveData();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_USER_REGISTER, data);
    }

    @Override
    public boolean isPhoneValid() {
        return getData().isPhoneValid;
    }

    @Override
    public void setPhoneValid(boolean isValid) {
        getData().isPhoneValid = isValid;
        saveData();
    }

    @Override
    public boolean isSessionIdExist() {
        return !TextUtils.isEmpty(getData().sessionId);
    }

    @Override
    public void resetCache() {
        this.data = null;
        cacheStorage.removeData(FileEnum.CACHE_USER_REGISTER);
    }


}
