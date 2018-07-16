package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;

public interface IPresenterRegister {
    void onClickRegister(String phoneText, boolean isAgeChecked, boolean isTermsChecked);

    void onClickRegisterConfirm(String textCheck);

    void onClickFemale();

    void onClickMale();

    void setListener(IPresenterRegisterListener listener);
}
