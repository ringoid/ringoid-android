package com.ringoid.controller.data;

public enum FileEnum {
    TOKEN("token", String.class);

    private Class className;
    private String filename;

    public String getFilename() {
        return filename;
    }

    FileEnum(String filename, Class classname) {
        this.filename = filename;
        this.className = classname;
    }

    public Class getClassname() {
        return className;
    }
}
