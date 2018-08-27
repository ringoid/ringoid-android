/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;
import com.ringoid.view.IViewPopup;

import javax.inject.Inject;

public class PresenterAdapterProfile implements IPresenterAdapterProfile {

    @Inject
    ICacheProfile cacheProfile;

    @Inject
    IViewPopup viewPopup;

    @Inject
    ICacheSettingsPrivacy cacheSettingsPrivacy;

    @Inject
    INavigator navigator;

    @Inject
    INavigatorPages navigatorPages;

    @Inject
    ICacheTutorial cacheTutorial;

    public PresenterAdapterProfile() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public String getUrl(int pos) {
        return "file:///android_asset/" + cacheProfile.getImage(pos);
    }

    @Override
    public int getLikesNum(int position) {
        return cacheProfile.getLikesNum(position);
    }

    @Override
    public boolean onClickItem(Context context, int position) {

        if (cacheTutorial.isShowDialogHiddenMode() && cacheSettingsPrivacy.isPrivacyPhotosNoone())
            return true;

        int messageRes = cacheSettingsPrivacy.isPrivacyPhotosOppositeSex()
                ? R.string.settings_privacy_photos_all
                : cacheSettingsPrivacy.isPrivacyPhotosLikes()
                ? R.string.settings_privacy_photos_liked
                : R.string.settings_privacy_photos_noone;
        viewPopup.showToast(String.format(context.getResources().getString(R.string.format_profile_photo_click_tutorial),
                context.getResources().getString(messageRes)));
        return false;
    }

    @Override
    public void onClickOK(boolean isShow) {
        cacheTutorial.setProfileDialogLikeShow(isShow);
    }

    @Override
    public void onClickLiked(boolean isShow) {
        cacheTutorial.setProfileDialogLikeShow(isShow);
        navigatorPages.navigateLikes();
    }

    @Override
    public boolean isDialogShowLikes() {
        return cacheTutorial.isShowDialogLikes();
    }

    @Override
    public void onClickAboutHiddenMode(boolean isShow) {
        cacheTutorial.setDialogHiddenModeShow(isShow);
        navigator.navigateWelcome(false);
    }

    @Override
    public void onClickHiddenModeOK(boolean isShow) {
        cacheTutorial.setDialogHiddenModeShow(isShow);
    }

    @Override
    public void onClickHiddenModePrivacy(boolean isShow) {
        cacheTutorial.setDialogHiddenModeShow(isShow);
        navigator.navigateSettingsPrivacy(true);
    }
}
