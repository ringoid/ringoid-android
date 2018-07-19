package org.byters.ringoid.controller.data.memorycache;

public interface ICacheRegister {
    int getSex();

    void setSexFemale();

    void setSexMale();

    boolean isDateBirthSelected();

    long getDateBirthMillis();

    void setDateBirthMillis(long timeInMillis);
}
