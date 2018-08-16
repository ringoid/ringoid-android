package com.ringoid.model;

public enum SEX {

    FEMALE(0), MALE(1);

    private int value;

    SEX(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
