/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

public interface ICacheRegister {
    int getSex();

    void setSexFemale();

    void setSexMale();

    int getYearBirth();

    boolean setDateBirth(int year);

    void setSessionId(String sessionId);

    String getSessionId();

    void setPhoneValid(boolean isValid);

    void setDateLegal();

    long getDateLegal();

    boolean isPhoneValid();

    boolean isSessionIdExist();

    void resetCache();

    boolean isValidSexAndBirth();
}
