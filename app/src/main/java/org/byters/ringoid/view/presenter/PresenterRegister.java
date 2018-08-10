package org.byters.ringoid.view.presenter;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterListener;
import org.byters.ringoid.model.SEX;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterRegister implements IPresenterRegister {

    @Inject
    IRepositoryRegister repositoryRegister;

    @Inject
    IRepositoryRegisterConfirm repositoryRegisterCodeConfirm;

    @Inject
    INavigator navigator;

    @Inject
    ICacheRegister cacheRegister;

    private ListenerRegisterConfirm listenerRegisterConfirm;
    private ListenerRegister listenerRegister;
    private WeakReference<IPresenterRegisterListener> refListener;

    public PresenterRegister() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryRegister.setListener(listenerRegister = new ListenerRegister());
        repositoryRegisterCodeConfirm.setListener(listenerRegisterConfirm = new ListenerRegisterConfirm());
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
    public void onClickLoginPhoneVerify(String phone) {
        if (TextUtils.isEmpty(phone)) {
            notifyError(R.string.error_phone);
            return;
        }
        repositoryRegister.request(phone);
    }

    @Override
    public void onClickCodeSMSConfirm(String code) {
        repositoryRegisterCodeConfirm.request(code);
    }

    @Override
    public void onClickRegister() {

        navigator.navigateFeed();
    }

    @Override
    public void onDataBirthSet(long timeInMillis) {
        cacheRegister.setDateBirthMillis(timeInMillis);
        showDateBirth(timeInMillis);
    }

    private void showDateBirth(long time) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showDateBirth(time);
    }

    private void loginGoNext() {

        if (refListener == null || refListener.get() == null) return;
        refListener.get().navigateNext();
    }

    private class ListenerRegister implements IRepositoryRegisterListener {
        @Override
        public void onSuccess() {
            loginGoNext();
        }
    }

    private class ListenerRegisterConfirm implements IRepositoryRegisterConfirmListener {
        @Override
        public void onSuccess(boolean isRegistered) {
            if (isRegistered)
                navigator.navigateFeed();
            else loginGoNext();
        }
    }

}
