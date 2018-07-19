package org.byters.ringoid.view.presenter;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.controller.data.memorycache.ICacheCountryList;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.memorycache.ICacheRegisterReferral;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheCountryListListener;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheRegisterReferralListener;
import org.byters.ringoid.controller.data.repository.IRepositoryCountryList;
import org.byters.ringoid.controller.data.repository.IRepositoryRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterReferralConfirm;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterReferralDescription;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterListener;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterReferralConfirmListener;
import org.byters.ringoid.model.DataCountry;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterRegister implements IPresenterRegister {

    private final ICacheCountryListListener listenerCacheCountryList;
    private final ListenerCacheRegisterReferral listenerCacheRegisterReferral;
    private final IRepositoryRegisterReferralConfirmListener listenerRepositoryRegisterReferralConfirm;

    @Inject
    IRepositoryRegisterReferralConfirm repositoryRegisterReferralConfirm;

    @Inject
    IRepositoryRegister repositoryRegister;

    @Inject
    IRepositoryRegisterConfirm repositoryRegisterCodeConfirm;

    @Inject
    IRepositoryCountryList repositoryCountryList;

    @Inject
    IRepositoryRegisterReferralDescription repositoryRegisterReferralDescription;

    @Inject
    INavigator navigator;

    @Inject
    ICacheRegister cacheRegister;

    @Inject
    ICacheCountryList cacheCountryList;

    @Inject
    ICacheRegisterReferral cacheRegisterReferral;

    private ListenerRegisterConfirm listenerRegisterConfirm;
    private ListenerRegister listenerRegister;
    private WeakReference<IPresenterRegisterListener> refListener;

    public PresenterRegister() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryRegister.setListener(listenerRegister = new ListenerRegister());
        repositoryRegisterCodeConfirm.setListener(listenerRegisterConfirm = new ListenerRegisterConfirm());
        cacheCountryList.addListener(listenerCacheCountryList = new ListenerCacheCountryList());
        cacheRegisterReferral.setListener(listenerCacheRegisterReferral = new ListenerCacheRegisterReferral());
        repositoryRegisterReferralConfirm.setListener(listenerRepositoryRegisterReferralConfirm = new ListenerRepositoryRegisterReferralConfirm());
    }

    private void notifyError(int stringId) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onError(stringId);
    }

    @Override
    public void onClickFemale() {
        cacheRegister.setSexFemale();
    }

    @Override
    public void onClickMale() {
        cacheRegister.setSexMale();
    }

    @Override
    public void setListener(IPresenterRegisterListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickLoginTermsAgreement(boolean isAgreementChecked) {
        if (!isAgreementChecked) {
            notifyError(R.string.error_agreement);
            return;
        }

        loginGoNext();
    }

    @Override
    public void onClickLoginPhoneVerify(String phone) {
        if (TextUtils.isEmpty(phone) || !cacheCountryList.isCountrySelected()) {
            notifyError(R.string.error_phone);
            return;
        }
        repositoryRegister.request(phone);
    }

    @Override
    public void onClickCountry() {
        repositoryCountryList.request();
        showAlertCountryList();
    }

    @Override
    public void onClickCodeSMSConfirm(String code) {
        repositoryRegisterCodeConfirm.request(code);
    }

    @Override
    public void onClickRegister(boolean ageChecked, boolean termsChecked) {
        if (!ageChecked || !termsChecked || !cacheRegister.isDateBirthSelected()) {
            notifyError(R.string.error_age_terms_date);
            return;
        }

        repositoryRegisterReferralDescription.request();
        loginGoNext();
    }

    @Override
    public void onClickDateBirth() {
        long dateMillis = cacheRegister.getDateBirthMillis();
        dialogDateBirthShow(dateMillis);
    }

    @Override
    public void onClickReferralConfirm(String linkReferral) {
        repositoryRegisterReferralConfirm.request(linkReferral);
    }

    @Override
    public void onDataBirthSet(long timeInMillis) {
        cacheRegister.setDateBirthMillis(timeInMillis);
    }

    @Override
    public void onClickReferralSkip() {
        repositoryRegisterReferralConfirm.request(null);
    }

    private void dialogDateBirthShow(long dateMillis) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().dialogDateBirthShow(dateMillis);
    }

    private void showAlertCountryList() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showAlertCountryList();
    }

    private void loginGoNext() {

        if (refListener == null || refListener.get() == null) return;
        refListener.get().navigateNext();
    }

    private void showCountry(String code, String title) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showCoutry(code, title);
    }

    private void dialogCountryListCancel() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().dialogCountryListCancel();
    }

    private void viewReferralShow(String title, String description) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().showReferralData(title, description);
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

    private class ListenerCacheCountryList implements ICacheCountryListListener {
        @Override
        public void onDataUpdate() {
        }

        @Override
        public void onSelectedItemUpdate(DataCountry country) {
            if (country == null) return;
            dialogCountryListCancel();
            showCountry(country.getCode(), country.getTitle());
        }
    }

    private class ListenerCacheRegisterReferral implements ICacheRegisterReferralListener {
        @Override
        public void onUpdate(String title, String description) {
            viewReferralShow(title, description);
        }
    }

    private class ListenerRepositoryRegisterReferralConfirm implements IRepositoryRegisterReferralConfirmListener {
        @Override
        public void onSuccess() {
            navigator.navigateFeed();
        }
    }
}
