package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class DataUser implements Serializable {
    private String code;
    private String phone;
    private boolean registered;
    private String customerID;
    private boolean userNew;

    public DataUser(String code, String phone) {
        this.code = code;
        this.phone = phone;
        registered = false;
        userNew = true;
    }

    public void setUserOld() {
        userNew = false;
    }

    public boolean isUserNew() {
        return userNew;
    }

    public void setUserNew() {
        userNew = true;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
