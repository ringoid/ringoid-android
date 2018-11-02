package com.ringoid.view.presenter.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheBlacklist;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheMessageCompose;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICachePhotoRemove;
import com.ringoid.controller.data.memorycache.ICachePhotoUpload;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.ICacheUser;
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
    INavigatorPages navigatorPages;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ICacheBlacklist cacheBlacklist;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheChatMessages cacheChatMessages;

    @Inject
    ICacheLikes cacheLikes;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheExplore cacheExplore;

    @Inject
    ICacheRegister cacheRegister;

    @Inject
    ICacheMessageCompose cacheMessageCompose;

    @Inject
    ICachePhotoUpload cachePhotoUpload;

    @Inject
    ICachePhotoRemove cachePhotoRemove;

    public LogoutHelper() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void logout() {
        if (cacheUser.isRegistered()) {
            cacheToken.resetCache();
            cacheUser.resetCache();
            cacheTutorial.resetCache();
            cacheBlacklist.resetCache();
            cacheProfile.resetCache();
            cacheChatMessages.resetCache();
            cacheInterfaceState.resetCache();
            cacheLikes.resetCache();
            cacheMessages.resetCache();
            cacheExplore.resetCache();
            cacheRegister.resetCache();
            cacheMessageCompose.resetCache();
            cachePhotoUpload.resetCache();
            cachePhotoRemove.resetCache();
        }
        navigator.navigateLogin();
    }
}
