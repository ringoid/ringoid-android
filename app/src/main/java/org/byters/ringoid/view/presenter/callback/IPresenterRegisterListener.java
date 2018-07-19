package org.byters.ringoid.view.presenter.callback;

public interface IPresenterRegisterListener {
    void onError(int stringId);

    void navigateNext();

    void showAlertCountryList();

    void dialogCountryListCancel();

    void showCoutry(String code, String title);

    void dialogDateBirthShow(long dateMillis);

    void showReferralData(String title, String description);
}
