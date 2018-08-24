package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.util.Locale;

public class CacheLocale implements ICacheLocale {
    @Override
    public String getLang() {
        return Locale.getDefault().getLanguage();
    }
}
