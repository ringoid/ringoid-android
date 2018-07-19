package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;

public interface IPresenterRegister {

    void onClickFemale();

    void onClickMale();

    void setListener(IPresenterRegisterListener listener);

    void onClickLoginTermsAgreement(boolean isAgreementChecked);

    void onClickLoginPhoneVerify(String phone);

    void onClickCountry();

    void onClickCodeSMSConfirm(String code);

    void onClickRegister(boolean ageChecked, boolean termsChecked);

    void onClickDateBirth();

    void onClickReferralConfirm(String linkReferral);

    void onDataBirthSet(long timeInMillis);

    void onClickReferralSkip();
}
