package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.repository.IRepositorySettingsGet;
import com.ringoid.controller.data.repository.IRepositorySettingsSave;

import javax.inject.Inject;

public class SettingsHelper implements ISettingsHelper {

    @Inject
    IRepositorySettingsSave repositorySettingsSave;

    @Inject
    IRepositorySettingsGet repositorySettingsGet;

    public SettingsHelper() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void requestSave() {
        repositorySettingsSave.request();
    }

    @Override
    public void requestGet() {
        repositorySettingsGet.request();
    }

}
