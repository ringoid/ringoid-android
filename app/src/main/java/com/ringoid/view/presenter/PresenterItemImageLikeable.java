package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;

import javax.inject.Inject;

public class PresenterItemImageLikeable implements IPresenterItemImageLikeable {

    private static int MAX_LIKES_DIALOG_SHOW = 3;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    INavigator navigator;

    @Inject
    IViewDialogs viewDialogs;

    public PresenterItemImageLikeable() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public boolean isHiddenMode() {
        return cacheSettingsPrivacy.isPrivacyPhotosNoone();
    }

    @Override
    public boolean isDialogHiddenShow() {
        return cacheTutorial.isShowDialogHiddenMode();
    }

    @Override
    public void onClickAbout(boolean b) {
        cacheTutorial.setDialogHiddenModeShow(b);
        navigator.navigateWelcome(false);
    }

    @Override
    public void onClickOK(boolean b) {
        cacheTutorial.setDialogHiddenModeShow(b);
    }

    @Override
    public void onClickPrivacy(boolean b) {
        cacheTutorial.setDialogHiddenModeShow(b);
        navigator.navigateSettingsPrivacy(true);
    }

    @Override
    public void checkLikesDialog() {
        if (cacheTutorial.getImageLikes() < MAX_LIKES_DIALOG_SHOW) return;

        cacheTutorial.resetLikesNum();
        viewDialogs.showDialogExplore();
    }
}
