package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;

import javax.inject.Inject;

public class PresenterItemImageLikeable implements IPresenterItemImageLikeable {

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    INavigator navigator;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    ICacheUser cacheUser;

    public PresenterItemImageLikeable() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public boolean isUserNew() {
        return cacheUser.isUserNew();
    }
}
