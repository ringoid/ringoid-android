/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data;

import com.ringoid.model.DataBlacklistPhones;
import com.ringoid.model.DataTutorial;
import com.ringoid.model.DataUser;

public enum FileEnum {
    TOKEN("token", String.class),
    USER("user", DataUser.class),
    TUTORIAL("tutorial", DataTutorial.class),
    BLACKLIST("blacklist", DataBlacklistPhones.class);

    private Class className;
    private String filename;

    FileEnum(String filename, Class classname) {
        this.filename = filename;
        this.className = classname;
    }

    public String getFilename() {
        return filename;
    }

    public Class getClassname() {
        return className;
    }
}
