/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.view.callback;

public interface IViewPhotoInputListener {
    void onDialogClose();

    void onPhoneDone();

    void onCodeEdit(String s);

    void onPhoneEdit(String s);
}
