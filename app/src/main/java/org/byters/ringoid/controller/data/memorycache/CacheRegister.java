package org.byters.ringoid.controller.data.memorycache;

public class CacheRegister implements ICacheRegister {
    private int sex;

    @Override
    public int getSex() {
        return sex;
    }

    @Override
    public void setSexFemale() {
        this.sex = 0;
    }

    @Override
    public void setSexMale() {
        this.sex = 1;
    }
}
