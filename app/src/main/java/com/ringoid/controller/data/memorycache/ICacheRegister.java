/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheRegister {
    int getSex();

    void setSexFemale();

    void setSexMale();

    boolean isDateBirthSelected();

    int getYearBirth();

    boolean setDateBirth(int year);

    void setSessionId(String sessionId);

    String getSessionId();

    void setPhoneValid(boolean isValid);

    void setDateTerms(boolean isChecked);

    void setDateAge(boolean isChecked);

    long getDateTerms();

    long getDateAge();

    long getDatePrivacy();

    boolean isPhoneValid();
}
