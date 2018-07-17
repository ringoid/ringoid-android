package org.byters.ringoid.view.presenter;

import android.text.TextUtils;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterConfirmListener;
import org.byters.ringoid.controller.data.repository.callback.IRepositoryRegisterListener;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterRegister implements IPresenterRegister {

    @Inject
    IRepositoryRegister repositoryRegister;

    @Inject
    IRepositoryRegisterConfirm repositoryRegisterConfirm;

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
        repositoryRegisterConfirm.setListener(listenerRegisterConfirm = new ListenerRegisterConfirm());
    }

    @Override
    public void onClickRegister(String phoneText, boolean isAgeChecked, boolean isTermsChecked) {
        if (!isAgeChecked || !isTermsChecked) {
            notifyError(R.string.error_terms);
            return;
        }

        if (TextUtils.isEmpty(phoneText)) {
            notifyError(R.string.error_phone);
            return;
        }

        repositoryRegister.request(phoneText);
    }

    private void notifyError(int stringId) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onError(stringId);
    }

    @Override
    public void onClickRegisterConfirm(String textCheck) {
        repositoryRegisterConfirm.request(textCheck);
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

    private class ListenerRegister implements IRepositoryRegisterListener {
        @Override
        public void onSuccess() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().navigateConfirm();
        }
    }

    private class ListenerRegisterConfirm implements IRepositoryRegisterConfirmListener {
        @Override
        public void onSuccess() {
            navigator.navigateFeed();
        }
    }
}
