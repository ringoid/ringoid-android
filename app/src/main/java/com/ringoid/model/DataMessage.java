/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

import java.io.Serializable;

public class DataMessage implements Serializable {

    private boolean isSelf;
    private String message;

    public DataMessage(boolean isSelf, String message) {
        this.isSelf = isSelf;
        this.message = message;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public String getMessage() {
        return message;
    }
}
