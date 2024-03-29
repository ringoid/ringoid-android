package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.repository.IRepositoryRegisterLogout;
import com.ringoid.view.INavigator;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.callback.IPresenterSettingsListener;
import com.ringoid.view.presenter.util.ILogoutHelper;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterSettings implements IPresenterSettings {

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ILogoutHelper logoutHelper;

    @Inject
    IRepositoryRegisterLogout repositoryRegisterLogout;

    @Inject
    INavigator navigator;

    private WeakReference<IPresenterSettingsListener> refListener;

    public PresenterSettings() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onCreateView() {
        scrollToSavedPosition();
    }

    private void scrollToSavedPosition() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().scrollToPosition(cacheInterfaceState.getPositionScrollPageSettings(), cacheInterfaceState.getPositionScrollPageSettingsOffset());
    }

    @Override
    public void setListener(IPresenterSettingsListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onScroll(int scrollY) {
        cacheInterfaceState.setPositionScrollSettings(0, scrollY);
    }

    @Override
    public void onConfirmLogout() {
        repositoryRegisterLogout.request();
        logoutHelper.logout();
    }

    @Override
    public void onClickTheme() {
        cacheInterfaceState.updateTheme();
    }

    @Override
    public boolean onBackPressed() {
        cacheInterfaceState.setCurrentPage(PAGE_ENUM.FEED_PROFILE);
        navigator.navigateFeed();
        return true;
    }

    @Override
    public boolean isThemeDark() {
        return cacheInterfaceState.isThemeDark();
    }

    @Override
    public void onThemeChanged(boolean isChecked) {
        cacheInterfaceState.setThemeDark(isChecked);
    }

}
