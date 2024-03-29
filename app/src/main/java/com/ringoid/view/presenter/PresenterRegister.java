/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheRegister;
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
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.callback.IPresenterRegisterListener;
import com.ringoid.view.ui.dialog.callback.IDialogErrorUnknownListener;
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

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    private ListenerTimer listenerTimer;
    private ListenerRegisterCodeConfirm listenerRegisterCodeConfirm;
    private ListenerRegisterPhone listenerRegisterPhone;
    private ListenerRegisterUserDetails listenerRegisterUserDetails;
    private View.OnClickListener listenerPopupPhoneConfirmError;
    private WeakReference<IPresenterRegisterListener> refListener;
    private IDialogErrorUnknownListener listenerDialogErrorUnknown;

    public PresenterRegister() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryRegisterPhone.setListener(listenerRegisterPhone = new ListenerRegisterPhone());
        repositoryRegisterCodeConfirm.setListener(listenerRegisterCodeConfirm = new ListenerRegisterCodeConfirm());
        repositoryRegisterUserDetails.setListener(listenerRegisterUserDetails = new ListenerRegisterUserDetails());
        listenerPopupPhoneConfirmError = new ListenerPhoneConfirmError();
        helperTimer.setListener(listenerTimer = new ListenerTimer());
        listenerDialogErrorUnknown = new DialogErrorUnknownListener();
    }

    @Override
    public void onClickFemale() {
        cacheRegister.setSexFemale();
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setGenderSelected(SEX.FEMALE);
        refListener.get().setRegisterButtonState(cacheRegister.isValidSexAndBirth());
    }

    @Override
    public void onClickMale() {
        cacheRegister.setSexMale();
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setGenderSelected(SEX.MALE);
        refListener.get().setRegisterButtonState(cacheRegister.isValidSexAndBirth());
    }

    @Override
    public void setListener(IPresenterRegisterListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickLoginPhoneVerify(String code, String phone, boolean isValid) {
        if (TextUtils.isEmpty(phone))
            return;

        cacheInterfaceState.resetCacheSavedPhone();

        if (cacheUser.isPhoneEqual(code, phone) && helperTimer.isTicking()) {
            loginGoCodeInput();
            return;
        }

        cacheRegister.setDateLegal();

        helperTimer.cancel();
        cacheUser.setPhone(code, phone);
        cacheRegister.setPhoneValid(isValid);
        setPhoneInputStateEnabled(false);
        repositoryRegisterPhone.request();
    }

    @Override
    public void onClickCodeSMSResend() {
        repositoryRegisterPhone.request();
    }

    @Override
    public void onClickWrongPhone() {
        setPhoneInput();
    }

    @Override
    public void onClickTheme() {
        cacheInterfaceState.updateTheme();
    }

    @Override
    public void onCodeEdit(String s) {
        cacheInterfaceState.setPhoneCode(s);
    }

    @Override
    public void onPhoneEdit(String s) {
        cacheInterfaceState.setPhone(s);
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
        if(!cacheRegister.isValidSexAndBirth()) return;

        refListener.get().setRegisterUserEnabled(false);
        repositoryRegisterUserDetails.request();
    }

    @Override
    public void onDataBirthSet(int year) {
        if (cacheRegister.setDateBirth(year))
            showDateBirth(year);
        refListener.get().setRegisterButtonState(cacheRegister.isValidSexAndBirth());
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

        if (cacheInterfaceState.isPhoneExist()) {
            refListener.get().setPhone(cacheInterfaceState.getPhoneCode(), cacheInterfaceState.getPhone());
            refListener.get().setPhoneSelectionEnd();
        } else if (cacheUser.isPhoneCodeExist() && cacheUser.isPhoneExist()) {
            refListener.get().setPhone(cacheUser.getPhoneCode(), cacheUser.getPhone());
            refListener.get().setPhoneSelectionEnd();
        } else
            refListener.get().setContryLocal();

        cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_PHONE);
        refListener.get().setPage(INDEX_PHONE_INPUT);
    }

    private void loginGoProfileUpdate() {
        if (refListener == null || refListener.get() == null) return;
        if (cacheRegister.getSex() != SEX.UNDEFINED.getValue())
            refListener.get().setGenderSelected(cacheRegister.getSex() == SEX.FEMALE.getValue() ? SEX.FEMALE : SEX.MALE);
        refListener.get().showDateBirth(cacheRegister.getYearBirth());
        refListener.get().setRegisterButtonState(cacheRegister.isValidSexAndBirth());
        cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_PROFILE);
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
        cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_CODE);
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
            //todo onError
            navigator.navigateErrorConnection();
            setPhoneInputStateEnabled(true);
        }

        @Override
        public void onErrorPhone() {
            if (refListener == null || refListener.get() == null) return;
            cacheRegister.resetCache();
            cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_PHONE);
            refListener.get().setPage(INDEX_PHONE_INPUT);
            setPhoneInputStateEnabled(true);
        }
    }

    private class ListenerRegisterCodeConfirm implements IRepositoryRegisterCodeConfirmListener {
        @Override
        public void onSuccess() {
            setSMSInputStateEnabled(true);
            if (cacheUser.isRegistered()) {
                hideKeyboard();
                cacheTutorial.setShowDialogPhotoAdded(false);
                cacheInterfaceState.setCurrentPage(PAGE_ENUM.FEED_PROFILE);
                navigator.navigateFeed();
            } else loginGoProfileUpdate();
        }

        @Override
        public void onErrorNoPendingClient() {
            setSMSInputStateEnabled(true);
            cacheRegister.resetCache();
            if (refListener == null || refListener.get() == null) return;
            cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_PHONE);
            refListener.get().setPage(INDEX_PHONE_INPUT);
        }

        @Override
        public void onErrorInvalidCode() {
            setSMSInputStateEnabled(true);
            if (refListener == null || refListener.get() == null) return;
            refListener.get().clearCodeInput();
        }

        @Override
        public void onErrorUnknown() {
            setSMSInputStateEnabled(true);
            cacheRegister.resetCache();
            helperTimer.cancel();
            if (refListener == null || refListener.get() == null) return;
            viewDialogs.showDialogErrorUnknown(listenerDialogErrorUnknown);
        }
    }

    private class ListenerRegisterUserDetails implements IRepositoryRegisterUserDetailsListener {
        @Override
        public void onSuccess() {
            hideKeyboard();
            navigator.navigateFeedProfilePhotoSelect();
        }

        @Override
        public void onErrorNoConnection() {
            navigator.navigateErrorConnection();
            refListener.get().setRegisterUserEnabled(true);
        }

        @Override
        public void onErrorUnknown() {
            if (refListener == null || refListener.get() == null) {
                return;
            }
            viewDialogs.showDialogErrorUnknown(new IDialogErrorUnknownListener() {
                @Override
                public void onDismiss() {
                    refListener.get().setRegisterUserEnabled(true);
                }

                @Override
                public void onConfirm() {
                    navigator.navigateFeedback(refListener.get().getAppContext());
                }
            });
        }
    }

    private class ListenerPhoneConfirmError implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (refListener == null || refListener.get() == null) return;
            cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_PHONE);
            refListener.get().setPage(INDEX_PHONE_INPUT);
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

    private class DialogErrorUnknownListener implements IDialogErrorUnknownListener {
        @Override
        public void onDismiss() {
            if (refListener == null || refListener.get() == null) return;
            cacheInterfaceState.setCurrentPage(PAGE_ENUM.LOGIN_PHONE);
            refListener.get().setPage(INDEX_PHONE_INPUT);
        }

        @Override
        public void onConfirm() {

        }
    }
}
