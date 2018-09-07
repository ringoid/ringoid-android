/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

import com.ringoid.model.SEX;

public interface IPresenterRegisterListener {
    void onError(int stringId);

    void navigateNext();

    void showDateBirth(int time);

    void setGenderSelected(SEX sex);

    void showToast(int stringRes);

    void showPhoneInput();

    void hideKeyboard();

    void clearCodeInput();
}
