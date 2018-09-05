package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.repository.IRepositoryRegisterLogout;
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;

import javax.inject.Inject;

public class LogoutHelper implements ILogoutHelper {

    @Inject
    INavigator navigator;

    @Inject
    ICacheUser cacheUser;

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    IRepositoryRegisterLogout repositoryRegisterLogout;

    @Inject
    INavigatorPages navigatorPages;

    public LogoutHelper() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void logout() {
        repositoryRegisterLogout.request();
        if (cacheUser.isRegistered()) {
            cacheToken.resetCache();
            cacheUser.resetCache();
            cacheTutorial.resetCache();
            navigatorPages.resetCurrentPage();
        }
        navigator.navigateLogin();
    }
}
