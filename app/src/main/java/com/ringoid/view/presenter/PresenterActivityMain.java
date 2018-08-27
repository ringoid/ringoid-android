/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;

import javax.inject.Inject;

public class PresenterActivityMain implements IPresenterActivityMain {

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheUser cacheUser;

    @Inject
    INavigator navigator;

    @Inject
    IViewDialogs viewDialogs;

    public PresenterActivityMain() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onCreateView(Context context, FragmentManager supportFragmentManager, int viewId) {
        navigator.set(supportFragmentManager, viewId);
        viewDialogs.set(context);

        if (cacheToken.isTokenExist()) {
            if (cacheUser.isRegistered())
                navigator.navigateFeed();
            else navigator.navigateProfileUpdate();
        } else navigator.navigateWelcome(true);
    }

    @Override
    public boolean onBackPressed() {
        return navigator.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        navigator.onActivityResult(requestCode, resultCode, data);
    }
}
