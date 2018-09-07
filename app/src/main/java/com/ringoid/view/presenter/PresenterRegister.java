/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;
import android.view.View;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.repository.IRepositoryRegisterCodeConfirm;
import com.ringoid.controller.data.repository.IRepositoryRegisterPhone;
import com.ringoid.controller.data.repository.IRepositoryRegisterUserDetails;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterCodeConfirmListener;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterPhoneListener;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterUserDetailsListener;
import com.ringoid.model.SEX;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterRegisterListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterRegister implements IPresenterRegister {


    @Inject
    IRepositoryRegisterPhone repositoryRegisterPhone;

    @Inject
    IRepositoryRegisterCodeConfirm repositoryRegisterCodeConfirm;

    @Inject
    IRepositoryRegisterUserDetails repositoryRegisterUserDetails;

    @Inject
    INavigator navigator;

    @Inject
    ICacheRegister cacheRegister;

    @Inject
    ICacheUser cacheUser;

    @Inject
    IViewPopup viewPopup;

    private ListenerRegisterCodeConfirm listenerRegisterCodeConfirm;
    private ListenerRegisterPhone listenerRegisterPhone;
    private ListenerRegisterUserDetails listenerRegisterUserDetails;
    private View.OnClickListener listenerPopupPhoneConfirmError;
    private WeakReference<IPresenterRegisterListener> refListener;

    public PresenterRegister() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryRegisterPhone.setListener(listenerRegisterPhone = new ListenerRegisterPhone());
        repositoryRegisterCodeConfirm.setListener(listenerRegisterCodeConfirm = new ListenerRegisterCodeConfirm());
        repositoryRegisterUserDetails.setListener(listenerRegisterUserDetails = new ListenerRegisterUserDetails());
        listenerPopupPhoneConfirmError = new ListenerPhoneConfirmError();
    }

    private void notifyError(int stringId) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onError(stringId);
    }

    @Override
    public void onClickFemale() {
        cacheRegister.setSexFemale();
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setGenderSelected(SEX.FEMALE);
    }

    @Override
    public void onClickMale() {
        cacheRegister.setSexMale();
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setGenderSelected(SEX.MALE);
    }

    @Override
    public void setListener(IPresenterRegisterListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickLoginTermsAgreement(boolean isAgreementChecked, boolean ageChecked) {
        if (!isAgreementChecked || !ageChecked) {
            notifyError(R.string.error_agreement);
            return;
        }

        loginGoNext();
    }

    @Override
    public void onClickLoginPhoneVerify(String code, String phone, boolean isValid) {
        if (TextUtils.isEmpty(phone)) {
            notifyError(R.string.error_phone);
            return;
        }

        cacheUser.setPhone(code, phone);
        cacheRegister.setPhoneValid(isValid);
        repositoryRegisterPhone.request();
        clearCodeConfirm();
        loginGoNext();
    }

    private void clearCodeConfirm() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().clearCodeInput();
    }

    @Override
    public void onClickCodeSMSConfirm(String param) {
        if (TextUtils.isEmpty(param) || param.length() != 4) {
            showToastSMSError();
            return;
        }

        repositoryRegisterCodeConfirm.request(param);
    }

    private void showToastSMSError() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showToast(R.string.error_sms_code_len);
    }

    @Override
    public void onClickRegister() {
        if (cacheRegister.getSex() == SEX.UNDEFINED.getValue()
                || cacheRegister.getYearBirth() == 0) return;

        repositoryRegisterUserDetails.request();
    }

    @Override
    public void onDataBirthSet(int year) {
        if (cacheRegister.setDateBirth(year))
            showDateBirth(year);
    }

    @Override
    public void onCreateView() {
        checkInputedData();
    }

    private void checkInputedData() {
        if (refListener == null || refListener.get() == null) return;
        if (cacheRegister.getSex() != SEX.UNDEFINED.getValue())
            refListener.get().setGenderSelected(cacheRegister.getSex() == SEX.FEMALE.getValue() ? SEX.FEMALE : SEX.MALE);
        refListener.get().showDateBirth(cacheRegister.getYearBirth());
    }

    @Override
    public void setCheckedTerms(boolean isChecked) {
        cacheRegister.setDateTerms(isChecked);
    }

    @Override
    public void setCheckedAge(boolean isChecked) {
        cacheRegister.setDateAge(isChecked);
    }

    private void showDateBirth(int year) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showDateBirth(year);
    }

    private void loginGoNext() {

        if (refListener == null || refListener.get() == null) return;
        refListener.get().navigateNext();
    }

    private void hideKeyboard() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().hideKeyboard();
    }

    private class ListenerRegisterPhone implements IRepositoryRegisterPhoneListener {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {
            viewPopup.showSnackbar(R.string.message_phone_confirm_error, R.string.message_retry, listenerPopupPhoneConfirmError);
        }

        @Override
        public void onErrorPhone() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().showPhoneInput();
        }
    }

    private class ListenerRegisterCodeConfirm implements IRepositoryRegisterCodeConfirmListener {
        @Override
        public void onSuccess() {
            if (cacheUser.isRegistered()) {
                hideKeyboard();
                navigator.navigateFeed();
            } else loginGoNext();
        }

        @Override
        public void onErrorNoPendingClient() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().showPhoneInput();
        }

        @Override
        public void onErrorInvalidCode() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().clearCodeInput();
        }
    }

    private class ListenerRegisterUserDetails implements IRepositoryRegisterUserDetailsListener {
        @Override
        public void onSuccess() {
            hideKeyboard();
            navigator.navigateFeed();
        }
    }

    private class ListenerPhoneConfirmError implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().showPhoneInput();
        }
    }
}
