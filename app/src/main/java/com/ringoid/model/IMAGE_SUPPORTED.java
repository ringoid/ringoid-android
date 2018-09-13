package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public enum IMAGE_SUPPORTED {
    x1(480, "480x640"),
    x2(720, "720x960"),
    x3(1080, "1080x1440"),
    x4(1440, "1440x1920");

    private int width;
    private String value;

    IMAGE_SUPPORTED(int width, String value) {
        this.width = width;
        this.value = value;
    }

    public static IMAGE_SUPPORTED getClosestWidthUp(int w) {
        IMAGE_SUPPORTED result = IMAGE_SUPPORTED.x4;

        for (int i = IMAGE_SUPPORTED.values().length - 1; i > 0; --i) {
            if (IMAGE_SUPPORTED.values()[i].getWidth() < w)
                break;
            result = IMAGE_SUPPORTED.values()[i];
        }

        return result;
    }

    public int getWidth() {
        return width;
    }

    public String getValue() {
        return value;
    }
}
