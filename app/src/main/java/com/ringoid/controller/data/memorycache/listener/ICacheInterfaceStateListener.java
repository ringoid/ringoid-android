/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache.listener;

public interface ICacheInterfaceStateListener {
    void onPageSelected(int num);

    void onDialogComposeShowState(boolean isShown);

    void onThemeUpdate();
}
