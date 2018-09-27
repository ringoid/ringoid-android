/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterRegisterListener;

public interface IPresenterRegister {

    void onClickFemale();

    void onClickMale();

    void setListener(IPresenterRegisterListener listener);

    void onClickLoginTermsAgreement(boolean isAgreementChecked, boolean checked);

    void onClickLoginPhoneVerify(String code, String phone, boolean isValid);

    void onClickCodeSMSConfirm(String code);

    void onClickRegister();

    void onDataBirthSet(int year);

    void onCreateView();

    void setCheckedTerms(boolean isChecked);

    void setCheckedAge(boolean isChecked);

    void onClickCodeSMSResend();
}
