/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.model;

public class DataMessage {

    private boolean isSelf;
    private String message;
    private String url;

    public DataMessage(boolean isSelf, String message, String url) {
        this.isSelf = isSelf;
        this.message = message;
        this.url = url;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
