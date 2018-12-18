/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

import android.content.Context;

import com.ringoid.model.SEX;

public interface IPresenterRegisterListener {

    void showDateBirth(int time);

    void setGenderSelected(SEX sex);

    void hideKeyboard();

    void clearCodeInput();

    void showPhoneHint(String phone);

    void setPhoneInputEnabled(boolean isEnabled);

    void setSMSInputEnabled(boolean isEnabled);

    void setSMSResendDisabled(int timeSeconds);

    void setSMSResendEnabled();

    void setRegisterUserEnabled(boolean isEnabled);

    void setPage(int indexProfileUpdate);

    void setPhone(int phoneCode, String phone);

    void setPhoneSelectionEnd();

    void setContryLocal();

    void setRegisterButtonState(boolean isValid);

    Context getAppContext();
}
