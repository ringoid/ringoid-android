package com.ringoid.controller.data.memorycache;

import com.ringoid.model.SEX;

public class CacheRegister implements ICacheRegister {
    private int sex;
    private long dateBirthMillis;

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
}
