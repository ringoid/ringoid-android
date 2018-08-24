/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.ringoid.model.SEX;

public class CacheRegister implements ICacheRegister {

    private int sex;
    private long dateBirthMillis;
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
        this.sex = SEX.FEMALE.getValue();
    }

    @Override
    public boolean isDateBirthSelected() {
        return dateBirthMillis != 0;
    }

    @Override
    public long getDateBirthMillis() {
        return dateBirthMillis;
    }

    @Override
    public void setDateBirthMillis(long timeInMillis) {
        this.dateBirthMillis = timeInMillis;
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
    public void setPhoneValid(boolean isValid) {
        this.isPhoneValid = isValid;
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
}
