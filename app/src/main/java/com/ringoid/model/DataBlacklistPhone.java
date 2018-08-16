package com.ringoid.model;

public class DataBlacklistPhone {
    private String phone;

    public DataBlacklistPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isPhoneEquals(String phone) {
        return phone.equals(this.phone);
    }
}
