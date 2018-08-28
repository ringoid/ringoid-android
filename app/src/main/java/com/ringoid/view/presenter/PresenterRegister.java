/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.repository.IRepositoryRegisterCodeConfirm;
import com.ringoid.controller.data.repository.IRepositoryRegisterPhone;
import com.ringoid.controller.data.repository.IRepositoryRegisterUserDetails;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterCodeConfirmListener;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterPhoneListener;
import com.ringoid.controller.data.repository.callback.IRepositoryRegisterUserDetailsListener;
import com.ringoid.model.SEX;
import com.ringoid.view.INavigator;
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
    ICacheTutorial cacheTutorial;

    @Inject
    ICacheToken cacheToken;

    private ListenerRegisterCodeConfirm listenerRegisterCodeConfirm;
    private ListenerRegisterPhone listenerRegisterPhone;
    private ListenerRegisterUserDetails listenerRegisterUserDetails;

    private WeakReference<IPresenterRegisterListener> refListener;

    public PresenterRegister() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryRegisterPhone.setListener(listenerRegisterPhone = new ListenerRegisterPhone());
        repositoryRegisterCodeConfirm.setListener(listenerRegisterCodeConfirm = new ListenerRegisterCodeConfirm());
        repositoryRegisterUserDetails.setListener(listenerRegisterUserDetails = new ListenerRegisterUserDetails());
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
        loginGoNext();
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
                || cacheRegister.getDateBirthMillis() == 0) return;

        repositoryRegisterUserDetails.request();
    }

    @Override
    public void onDataBirthSet(long timeInMillis) {
        cacheRegister.setDateBirthMillis(timeInMillis);
        showDateBirth(timeInMillis);
    }

    @Override
    public void onCreateView() {
        if (cacheUser.isRegistered()) {
            cacheUser.resetCache();
            cacheTutorial.resetCache();
        }

        checkInputedData();
    }

    private void checkInputedData() {
        if (refListener == null || refListener.get() == null) return;
        if (cacheRegister.getSex() != SEX.UNDEFINED.getValue())
            refListener.get().setGenderSelected(cacheRegister.getSex() == SEX.FEMALE.getValue() ? SEX.FEMALE : SEX.MALE);
        refListener.get().showDateBirth(cacheRegister.getDateBirthMillis());
    }

    @Override
    public void setCheckedTerms(boolean isChecked) {
        cacheRegister.setDateTerms(isChecked);
    }

    @Override
    public void setCheckedAge(boolean isChecked) {
        cacheRegister.setDateAge(isChecked);
    }

    private void showDateBirth(long time) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showDateBirth(time);
    }

    private void loginGoNext() {

        if (refListener == null || refListener.get() == null) return;
        refListener.get().navigateNext();
    }

    private class ListenerRegisterPhone implements IRepositoryRegisterPhoneListener {
        @Override
        public void onSuccess() {

        }
    }

    private class ListenerRegisterCodeConfirm implements IRepositoryRegisterCodeConfirmListener {
        @Override
        public void onSuccess() {
            if (cacheUser.isRegistered())
                navigator.navigateFeed();
            else loginGoNext();
        }
    }

    private class ListenerRegisterUserDetails implements IRepositoryRegisterUserDetailsListener {
        @Override
        public void onSuccess() {
            navigator.navigateFeed();
        }
    }
}
