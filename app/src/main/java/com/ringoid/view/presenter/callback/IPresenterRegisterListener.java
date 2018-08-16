package com.ringoid.view.presenter.callback;

import com.ringoid.model.SEX;

public interface IPresenterRegisterListener {
    void onError(int stringId);

    void navigateNext();

    void showDateBirth(long time);

    void setGenderSelected(SEX sex);

    void showToast(int stringRes);
}
