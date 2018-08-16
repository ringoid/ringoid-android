package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterRegisterListener;

public interface IPresenterRegister {

    void onClickFemale();

    void onClickMale();

    void setListener(IPresenterRegisterListener listener);

    void onClickLoginTermsAgreement(boolean isAgreementChecked, boolean checked);

    void onClickLoginPhoneVerify(String phone);

    void onClickCodeSMSConfirm(String code);

    void onClickRegister();

    void onDataBirthSet(long timeInMillis);
}
