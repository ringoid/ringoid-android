package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.repository.IRepositorySettingsGet;
import com.ringoid.controller.data.repository.IRepositorySettingsSave;
import com.ringoid.controller.data.repository.callback.IRepositoryListenerBase;
import com.ringoid.view.INavigator;

import javax.inject.Inject;

public class SettingsHelper implements ISettingsHelper {

    private final IRepositoryListenerBase listenerRepositoryBase;
    @Inject
    IRepositorySettingsSave repositorySettingsSave;
    @Inject
    IRepositorySettingsGet repositorySettingsGet;
    @Inject
    INavigator navigator;

    public SettingsHelper() {
        ApplicationRingoid.getComponent().inject(this);
        listenerRepositoryBase = new ListenerRepositoryBase();
        repositorySettingsGet.setListener(listenerRepositoryBase);
        repositorySettingsSave.setListener(listenerRepositoryBase);
    }

    @Override
    public void requestSave() {
        repositorySettingsSave.request();
    }

    @Override
    public void requestGet() {
        repositorySettingsGet.request();
    }

    private class ListenerRepositoryBase implements IRepositoryListenerBase {
        @Override
        public void onTokenInvalid() {
            navigator.navigateLogin(false);
        }
    }
}
