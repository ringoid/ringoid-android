/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

import com.ringoid.BuildConfig;

import java.io.Serializable;
import java.util.UUID;

public class DataMessage implements Serializable {

    private boolean isSelf;
    private String message;
    private String id;

    public DataMessage(boolean isSelf, String message) {
        this.isSelf = isSelf;
        this.message = message;
        if (BuildConfig.DEBUG)
            id = UUID.randomUUID().toString();
        else throw new IllegalArgumentException();
    }

    public boolean isSelf() {
        return isSelf;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
}
