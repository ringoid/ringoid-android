/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.os.Bundle;

import com.ringoid.view.presenter.callback.IPresenterRegisterListener;

public interface IPresenterRegister {

    void onClickFemale();

    void onClickMale();

    void setListener(IPresenterRegisterListener listener);

    void onClickLoginPhoneVerify(String code, String phone, boolean isValid);

    void onClickCodeSMSConfirm(String code);

    void onClickRegister();

    void onDataBirthSet(int year);

    void onCreateView(Bundle arguments);

    void onClickCodeSMSResend();

    void onClickWrongPhone();

    void onClickTheme();
}
