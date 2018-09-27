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
        saveData();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.USER, data);
    }

    @Override
    public int getPhoneCode() {
        DataUser data = getData();
        if (data == null) return 0;

        int code;

        try {
            code = Integer.valueOf(data.getCode().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }

        return code;
    }

    @Override
    public String getPhone() {
        DataUser data = getData();
        return data == null ? null : data.getPhone();
    }

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.USER);
    }

    @Override
    public boolean isRegistered() {
        DataUser data = getData();
        return data == null ? false : data.isRegistered();
    }

    @Override
    public void setRegistered(boolean registered) {
        DataUser user = getData();
        if (user == null) this.data = new DataUser("", "");
        data.setRegistered(registered);
        saveData();
    }

    @Override
    public String getCustomerID() {
        DataUser data = getData();
        if (data == null) return null;
        return data.getCustomerID();
    }

    @Override
    public void setCustomerID(String customerID) {
        DataUser data = getData();
        if (data == null) return;
        data.setCustomerID(customerID);
        saveData();
    }

    @Override
    public boolean isUserNew() {
        DataUser data = getData();
        if (data == null) return true;
        return data.isUserNew();
    }

    @Override
    public void setUserNew() {
        DataUser data = getData();
        if (data == null) return;
        data.setUserNew();
        saveData();
    }

    @Override
    public void setUserOld() {
        DataUser data = getData();
        if (data == null) return;
        data.setUserOld();
        saveData();
    }

    @Override
    public boolean isPhoneEqual(String code, String phone) {
        return getPhone() != null
                && getPhone().equals(phone)
                && getData() != null
                && getData().getCode() != null
                && getData().getCode().equals(code);
    }

    private DataUser getData() {
        if (data == null) data = cacheStorage.readObject(FileEnum.USER, DataUser.class);
        return data;
    }
}
