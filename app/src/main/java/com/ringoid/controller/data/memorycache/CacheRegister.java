/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.model.SEX;

public class CacheRegister implements ICacheRegister {

    private int sex;
    private int dateBirth;
    private String sessionId;
    private boolean isPhoneValid;
    private long dateTerms, dateAge;

    @Override
    public int getSex() {
        return sex;
    }

    @Override
    public void setSexFemale() {
        this.sex = SEX.FEMALE.getValue();
    }

    @Override
    public void setSexMale() {
        this.sex = SEX.MALE.getValue();
    }

    @Override
    public boolean isDateBirthSelected() {
        return dateBirth != 0;
    }

    @Override
    public int getYearBirth() {
        return dateBirth;
    }

    @Override
    public boolean setDateBirth(int year) {
        if (dateBirth == year) return false;
        this.dateBirth = year;
        return true;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public long getDateTerms() {
        return dateTerms;
    }

    @Override
    public void setDateTerms(boolean isChecked) {
        dateTerms = isChecked ? System.currentTimeMillis() : 0;
    }

    @Override
    public long getDateAge() {
        return dateAge;
    }

    @Override
    public void setDateAge(boolean isChecked) {
        dateAge = isChecked ? System.currentTimeMillis() : 0;
    }

    @Override
    public long getDatePrivacy() {
        return dateTerms;
    }

    @Override
    public boolean isPhoneValid() {
        return isPhoneValid;
    }

    @Override
    public void setPhoneValid(boolean isValid) {
        this.isPhoneValid = isValid;
    }
}
