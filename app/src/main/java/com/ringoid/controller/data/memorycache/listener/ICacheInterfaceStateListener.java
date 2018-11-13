/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache.listener;

import com.ringoid.view.PAGE_ENUM;

public interface ICacheInterfaceStateListener {
    void onPageSelected(PAGE_ENUM num);

    void onDialogComposeShowState(boolean isShown);

    void onThemeUpdate();

    void onPhotoSelectedUpdated();
}
