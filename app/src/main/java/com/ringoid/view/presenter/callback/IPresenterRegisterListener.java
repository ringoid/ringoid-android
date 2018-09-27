/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

import com.ringoid.model.SEX;

public interface IPresenterRegisterListener {

    void navigateNext();

    void showDateBirth(int time);

    void setGenderSelected(SEX sex);

    void showPhoneInput();

    void hideKeyboard();

    void clearCodeInput();

    void showPhoneHint(String phone);

    void setPhoneInputEnabled(boolean isEnabled);

    void setSMSInputEnabled(boolean isEnabled);

    void setSMSResendDisabled(int timeSeconds);

    void setSMSResendEnabled();

    void showCodeInput();
}
