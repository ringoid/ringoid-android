/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid;

import android.app.Application;

public class ApplicationRingoid extends Application {


    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
