package com.ringoid.view.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.view.INavigator;

import javax.inject.Inject;

public class PresenterActivityMain implements IPresenterActivityMain {

    @Inject
    ICacheToken cacheToken;

    @Inject
    INavigator navigator;

    public PresenterActivityMain() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onCreateView(FragmentManager supportFragmentManager, int viewId) {
        navigator.set(supportFragmentManager, viewId);

        if (cacheToken.isTokenExist())
            navigator.navigateFeed();
        else navigator.navigateWelcome(true);
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
