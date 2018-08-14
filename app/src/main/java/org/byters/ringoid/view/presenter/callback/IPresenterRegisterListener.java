package org.byters.ringoid.view.presenter.callback;

import org.byters.ringoid.model.SEX;

public interface IPresenterRegisterListener {
    void onError(int stringId);

    void navigateNext();

    void showDateBirth(long time);

    void setGenderSelected(SEX sex);

    void showToast(int stringRes);
}
