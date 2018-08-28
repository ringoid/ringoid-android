/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

public enum SEX {

    FEMALE(2), MALE(1), UNDEFINED(0);

    private int value;

    SEX(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
