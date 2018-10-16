package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;

public class ModelUserRegister implements Serializable{

    public int sex;
    public int yearOfBirth;
    public String sessionId;
    public boolean isPhoneValid;
    public long dateTerms, dateAge;
}
