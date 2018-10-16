/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.os.Bundle;
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
import com.ringoid.view.ui.util.IHelperTimer;
import com.ringoid.view.ui.util.listener.IHelperTimerListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterRegister implements IPresenterRegister {

    public static final int INDEX_PHONE_INPUT = 0;
    public static final int INDEX_CODE_INPUT = 1;
    public static final int INDEX_PROFILE_UPDATE = 2;
    public static final String ARG_PAGE = "arg_profile_update";

    private static final int SMS_CODE_LENGTH = 4;

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

    @Inject
    IHelperTimer helperTimer;

    private ListenerTimer listenerTimer;
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
        helperTimer.setListener(listenerTimer = new ListenerTimer());
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
    public void onClickLoginPhoneVerify(String code, String phone, boolean isValid) {
        if (TextUtils.isEmpty(phone))
            return;

        if (cacheUser.isPhoneEqual(code, phone) && helperTimer.isTicking()) {
            loginGoCodeInput();
            return;
        }

        cacheRegister.setDateTerms();
        cacheRegister.setDateAge();

        helperTimer.cancel();
        cacheUser.setPhone(code, phone);
        cacheRegister.setPhoneValid(isValid);
        repositoryRegisterPhone.request();
        setPhoneInputStateEnabled(false);
    }


    @Override
    public void onClickCodeSMSResend() {
        repositoryRegisterPhone.request();
    }

    @Override
    public void onClickWrongPhone() {
        setPhoneInput();
    }

    private void setPhoneInputStateEnabled(boolean isEnabled) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setPhoneInputEnabled(isEnabled);
    }

    @Override
    public void onClickCodeSMSConfirm(String param) {
        if (TextUtils.isEmpty(param) || param.length() != SMS_CODE_LENGTH) {
            return;
        }

        repositoryRegisterCodeConfirm.request(param);
        setSMSInputStateEnabled(false);
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
    public void onCreateView(Bundle arguments) {
        setInitPage(arguments);
    }

    private void setInitPage(Bundle arguments) {
        if (arguments == null) return;

        int page_index = arguments.getInt(ARG_PAGE, INDEX_PHONE_INPUT);

        if (page_index == INDEX_CODE_INPUT)
            loginGoCodeInput();

        if (page_index == INDEX_PROFILE_UPDATE)
            loginGoProfileUpdate();

        if (page_index == INDEX_PHONE_INPUT)
            setPhoneInput();
    }

    private void setPhoneInput() {
        if (refListener == null || refListener.get() == null) return;
        if (cacheUser.isPhoneCodeExist() && cacheUser.isPhoneExist()) {
            refListener.get().setPhone(cacheUser.getPhoneCode(), cacheUser.getPhone());
            refListener.get().setPhoneSelectionEnd();
        }
        refListener.get().setPage(PresenterRegister.INDEX_PHONE_INPUT);
    }

    private void loginGoProfileUpdate() {
        if (refListener == null || refListener.get() == null) return;
        if (cacheRegister.getSex() != SEX.UNDEFINED.getValue())
            refListener.get().setGenderSelected(cacheRegister.getSex() == SEX.FEMALE.getValue() ? SEX.FEMALE : SEX.MALE);
        refListener.get().showDateBirth(cacheRegister.getYearBirth());
        refListener.get().setPage(INDEX_PROFILE_UPDATE);
    }

    private void showDateBirth(int year) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showDateBirth(year);
    }

    private void hideKeyboard() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().hideKeyboard();
    }

    private void setSMSInputStateEnabled(boolean isEnabled) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setSMSInputEnabled(isEnabled);
    }

    private void updateStateSMSResend(long timeMillis) {
        if (refListener == null || refListener.get() == null) return;

        if (timeMillis == 0)
            refListener.get().setSMSResendEnabled();
        else refListener.get().setSMSResendDisabled((int) (timeMillis / 1000));
    }

    private void loginGoCodeInput() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().clearCodeInput();
        refListener.get().showPhoneHint(String.format("+%d %s", cacheUser.getPhoneCode(), cacheUser.getPhone()));
        refListener.get().setPage(INDEX_CODE_INPUT);
        updateStateSMSResend(helperTimer.getMillis());
    }

    private class ListenerRegisterPhone implements IRepositoryRegisterPhoneListener {
        @Override
        public void onSuccess() {
            loginGoCodeInput();
            helperTimer.start();
            setPhoneInputStateEnabled(true);
        }

        @Override
        public void onError() {
            viewPopup.showSnackbar(R.string.message_phone_confirm_error, R.string.message_retry, listenerPopupPhoneConfirmError);
            setPhoneInputStateEnabled(true);
        }

        @Override
        public void onErrorPhone() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().showPhoneInput();
            setPhoneInputStateEnabled(true);
        }
    }

    private class ListenerRegisterCodeConfirm implements IRepositoryRegisterCodeConfirmListener {
        @Override
        public void onSuccess() {
            setSMSInputStateEnabled(true);
            if (cacheUser.isRegistered()) {
                hideKeyboard();
                navigator.navigateFeed();
            } else loginGoProfileUpdate();
        }

        @Override
        public void onErrorNoPendingClient() {
            setSMSInputStateEnabled(true);
            cacheRegister.resetCache();
            if (refListener == null || refListener.get() == null) return;
            refListener.get().showPhoneInput();
        }

        @Override
        public void onErrorInvalidCode() {
            setSMSInputStateEnabled(true);
            if (refListener == null || refListener.get() == null) return;
            refListener.get().clearCodeInput();
        }
    }

    private class ListenerRegisterUserDetails implements IRepositoryRegisterUserDetailsListener {
        @Override
        public void onSuccess() {
            hideKeyboard();
            navigator.navigatePhotoAdd();
        }
    }

    private class ListenerPhoneConfirmError implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().showPhoneInput();
        }
    }

    private class ListenerTimer implements IHelperTimerListener {
        @Override
        public void onTick(long millisUntilFinished) {
            updateStateSMSResend(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            updateStateSMSResend(0);
        }
    }
}
